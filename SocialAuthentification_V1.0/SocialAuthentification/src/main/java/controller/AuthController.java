package controller;

import java.io.IOException;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class AuthController {
	private static final String apiKey = "352507544885837";
	private static final String secretKey = "74f29c06985b1956d0f196bd495236e7";
	private static final String urlfinal="http://localhost:8080/SocialAuthentification/loginConfirm.htm";
	private static final String redirectUri = "http://localhost:8080/SocialAuthentification/loginConfirm.htm&scope=publish_stream";
	private static String nom="";
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
	@RequestMapping(value="loginConfirm", method=RequestMethod.GET, params="code") 
	public String authentifcation(@RequestParam String code){
		System.out.println("le code est:"+code);
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, urlfinal, null);
		 Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
		 Facebook facebook = connection.getApi();
		 if(facebook.isAuthorized()){
		 System.out.println("autorisation ="+facebook.isAuthorized());
		 }
		 nom=facebook.userOperations().getUserProfile().getName();
		return "redirect:/loginsucces.htm";
	}
	/**
	 * si le user n'accept pas l'application 
	 * @param error
	 * @return autentification echoue
	 */
	@RequestMapping(value="loginConfirm", method=RequestMethod.GET, params="error") 
	public String authentifcationno(@RequestParam String error){		
		return "redirect:/login/loginfeiled.jsp";
	}
	// page de redirection facultatif
	@RequestMapping("/loginsucces")
	public String redirection(Model model){
		model.addAttribute("nom",nom);
		return "login/loginsucces";
	}
	// retour a la page d'authentification
	/**
	 * 
	 * @return page d'index
	 */
	@RequestMapping("/back")
	public String retourIndex(){
		return "redirect:/index.jsp";
	}
	
}
