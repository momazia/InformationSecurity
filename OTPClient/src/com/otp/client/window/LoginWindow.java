package com.otp.client.window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.otp.client.model.User;

/**
 * Main login window class.
 * 
 * @author Max
 *
 */
public class LoginWindow {

	protected Shell shlOtpClient;
	private Text txtUsername;
	private Text txtPassword;
	private StyledText txtLog;
	private Combo comboServerAdd;
	private Map<String, User> users = new HashMap<>(); // Holds the data
														// locally.
	private static final int MAX_HASH_NUM = 5;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginWindow window = new LoginWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlOtpClient.open();
		shlOtpClient.layout();
		while (!shlOtpClient.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlOtpClient = new Shell();
		shlOtpClient.setToolTipText("Username");
		shlOtpClient.setSize(650, 500);
		shlOtpClient.setText("OTP Client");

		CLabel lblNewLabel = new CLabel(shlOtpClient, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setBounds(286, 35, 61, 21);
		lblNewLabel.setText("Login");

		txtUsername = new Text(shlOtpClient, SWT.BORDER);
		txtUsername.setToolTipText("Username");
		txtUsername.setBounds(248, 62, 248, 21);

		CLabel lblNewLabel_1 = new CLabel(shlOtpClient, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.RIGHT);
		lblNewLabel_1.setBounds(121, 62, 121, 21);
		lblNewLabel_1.setText("Username");

		txtPassword = new Text(shlOtpClient, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setToolTipText("Password");
		txtPassword.setBounds(248, 89, 248, 21);

		CLabel lblPassword = new CLabel(shlOtpClient, SWT.NONE);
		lblPassword.setAlignment(SWT.RIGHT);
		lblPassword.setText("Password");
		lblPassword.setBounds(121, 89, 121, 21);

		txtLog = new StyledText(shlOtpClient, SWT.BORDER | SWT.READ_ONLY);
		txtLog.setBounds(10, 176, 614, 275);

		Button btnLogin = new Button(shlOtpClient, SWT.NONE);
		btnLogin.addSelectionListener(loginButtonListener);
		btnLogin.setBounds(409, 145, 87, 25);
		btnLogin.setText("Login");

		Button BtnReset = new Button(shlOtpClient, SWT.NONE);
		BtnReset.addSelectionListener(resetButtonListener);
		BtnReset.setText("Reset");
		BtnReset.setBounds(248, 145, 87, 25);

		comboServerAdd = new Combo(shlOtpClient, SWT.NONE);
		comboServerAdd.setBounds(248, 116, 248, 23);
		comboServerAdd.add("http://localhost:8888");
		comboServerAdd.add("http://is-191t-otp.appspot.com");
		comboServerAdd.select(0);

		CLabel lblServerAddress = new CLabel(shlOtpClient, SWT.NONE);
		lblServerAddress.setText("Server Address");
		lblServerAddress.setAlignment(SWT.RIGHT);
		lblServerAddress.setBounds(121, 116, 121, 21);
	}

	/**
	 * Reset button listener
	 */
	private SelectionAdapter resetButtonListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			// Resetting all the fields
			txtPassword.setText("");
			txtUsername.setText("");
			txtLog.setText("");
		}
	};

	/**
	 * Login button listener
	 */
	private SelectionAdapter loginButtonListener = new SelectionAdapter() {
		@Override
		public void widgetSelected(SelectionEvent e) {
			// Getting the username and password
			String username = txtUsername.getText();
			String password = txtPassword.getText();
			String serverAddress = comboServerAdd.getText();

			boolean userLoggedInBefore = isUserLoggedInBefore(username);

			if (userLoggedInBefore) {
				log("User [" + username + "] has logged in before");
			} else {
				log("It is the first time user [" + username + "] is logging in");
				registerUser(username, password, serverAddress);
			}
		}

	};

	/**
	 * Registers the user in local storage and initializes OTP for the first
	 * time.
	 * 
	 * @param username
	 * @param password
	 * @param serverAddress
	 */
	private void registerUser(String username, String password, String serverAddress) {
		log("Registering user [" + username + "]...");

		// Generating a new random seed
		String seed = createRandomSeed();
		log("Seed [" + seed + "] is created for the user [" + username + "]");

		// Calling the server to initialize the OTP
		try {
			initializeOTP(username, password, serverAddress, seed);
		} catch (Exception e) {
			log(e.getMessage());
		}
	}

	/**
	 * Initializes OTP for the given username.
	 * 
	 * @param username
	 * @param password
	 * @param serverAddress
	 * @param seed
	 * @throws IllegalArgumentException
	 * @throws NoSuchAlgorithmException
	 */
	private void initializeOTP(String username, String password, String serverAddress, String seed) throws IllegalArgumentException, NoSuchAlgorithmException {
		log("Generating [" + MAX_HASH_NUM + "] hashes out of seed [" + seed + "]...");
		String randomHash = hash(seed, MAX_HASH_NUM);
		log("Calling selected server [" + serverAddress + "] to initialize the OTP for user [" + username + "] ...");
		String response = get(username, password, serverAddress + "/otp/initialize", Arrays.asList(new BasicNameValuePair[] { new BasicNameValuePair("currentHash", randomHash) }));
		boolean success = isSuccess(response);
		if (success) {
			log("User [" + username + "]'s OTP is all set on the server.");
			saveOTP(username, seed, MAX_HASH_NUM);
			log("User [" + username + "]'s OTP data is saved locally.");
		} else {
			log("Something went wrong while creating OTP on the server side!");
		}
	}

	/**
	 * Saves the OTP information about the user locally.
	 * 
	 * @param username
	 * @param seed
	 * @param counter
	 */
	private void saveOTP(String username, String seed, int counter) {
		users.put(username, new User(seed, counter));
	}

	/**
	 * Checks the response coming from server to make sure everything is fine.
	 * 
	 * @param response
	 * @return
	 */
	private boolean isSuccess(String response) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject obj = (JSONObject) parser.parse(response);
			return Boolean.TRUE.equals(obj.get("data"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Applies SHA-1 to the input passed.
	 *
	 * @param input
	 * @param numberOfHash
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String hash(String input, int numberOfHash) throws NoSuchAlgorithmException {
		String result = input;
		for (int i = 0; i < numberOfHash; i++) {
			result = DigestUtils.sha1Hex(result);
			log("Hash[" + (i + 1) + "]: [" + result + "]");
		}
		return result;
	}

	/**
	 * Calls the URL passed using GET and by applying basic auth using username
	 * and password.
	 * 
	 * @param username
	 * @param password
	 * @param url
	 * @param params
	 * @return
	 * @throws IllegalArgumentException
	 */
	private String get(String username, String password, String url, List<BasicNameValuePair> params) throws IllegalArgumentException {

		try {
			CredentialsProvider provider = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			provider.setCredentials(AuthScope.ANY, credentials);
			HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
			HttpGet request = new HttpGet(url + '?' + URLEncodedUtils.format(params, "utf-8"));
			HttpResponse response = client.execute(request);
			// Validation on the response code
			if (response.getStatusLine().getStatusCode() == 401) {
				throw new IllegalArgumentException("Wrong username/password: HTTP error code: " + response.getStatusLine().getStatusCode());
			}
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			// Reading the response back
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			StringBuffer result = new StringBuffer();
			String output;
			while ((output = br.readLine()) != null) {
				result.append(output);
			}
			return result.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Creates a random Seed of length 5.
	 *
	 * @return
	 */
	private String createRandomSeed() {
		return UUID.randomUUID().toString().substring(0, 5);
	}

	/**
	 * Checks to see if the username given was logged in before or it is the
	 * first time logging in.
	 * 
	 * @param username
	 * @return
	 */
	private boolean isUserLoggedInBefore(String username) {
		return users.containsKey(username);
	}

	/**
	 * Logs the given text into txtLog.
	 * 
	 * @param log
	 */
	private void log(String log) {
		txtLog.append(log + "\n");
	}
}
