package controller;

import java.io.IOException;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AuthController {
	private static final String apiKey = "352507544885837";
	private static final String secretKey = "74f29c06985b1956d0f196bd495236e7";
	private static final String urlfinal="http://localhost:8080/SocialAuthentification/loginConfirm.htm";
	private static final String redirectUri = "http://localhost:8080/SocialAuthentification/loginConfirm.htm&scope=publish_stream";
	OAuth2Operations oauthOperations;
	FacebookConnectionFactory connectionFactory;
	/**
	 * 
	 * page initial de login jdialog de facebook
	 * @return
	 */
	@RequestMapping("/login")
	public String pageLogin(){
	    String url="https://www.facebook.com/dialog/oauth?client_id=" + apiKey + "&redirect_uri=" + redirectUri;
		connectionFactory = new FacebookConnectionFactory(apiKey, secretKey);
		oauthOperations = connectionFactory.getOAuthOperations();		
		System.out.println("daz man hna !!!");
		return "redirect:"+url;		
	}
	/**
	 *  confirmation de l'acces par application 
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("loginConfirm")
	public String authentifcation(@RequestParam String code) throws IOException{
		System.out.println("le code est:"+code);
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, urlfinal, null);
		 Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		 Facebook facebook = connection.getApi();
		 //facebook.feedOperations().updateStatus("Hello World");
		return "redirect:/loginsucces.htm";
	}
	// page de redirection facultatif
	@RequestMapping("/loginsucces")
	public String redirection(){
		return "redirect:/login/loginsucces.jsp";
	}
	
}
