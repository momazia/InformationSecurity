package com.otp.security;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.opt.model.User;
import com.otp.service.UserService;
import com.otp.util.PasswordUtils;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final String email = authentication.getName();
		String password = (String) authentication.getCredentials();

		if (StringUtils.isBlank(email)) {
			throw new BadCredentialsException(messageSource.getMessage("NotEmpty.loginForm.username", null, LocaleContextHolder.getLocale()));
		}

		@SuppressWarnings("deprecation")
		User user = userService.loadUserByUsername(email);

		if (user == null || !user.getUsername().equalsIgnoreCase(email)) {
			throw new BadCredentialsException(messageSource.getMessage("NotFound.loginForm.username", new String[] { email }, LocaleContextHolder.getLocale()));
		}

		if (!PasswordUtils.getInstance().matches(password, user.getPassword())) {
			throw new BadCredentialsException(messageSource.getMessage("Invalid.loginForm.password", new String[] { email }, LocaleContextHolder.getLocale()));
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	public boolean supports(Class<?> arg0) {
		return true;
	}

}
