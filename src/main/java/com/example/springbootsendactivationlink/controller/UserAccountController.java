package com.example.springbootsendactivationlink.controller;

import com.example.springbootsendactivationlink.entity.ConfirmationToken;
import com.example.springbootsendactivationlink.entity.UserEntity;
import com.example.springbootsendactivationlink.service.ConfirmationTokenService;
import com.example.springbootsendactivationlink.service.UserTokenService;
import com.example.springbootsendactivationlink.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAccountController {

    @Autowired
    private UserTokenService userTokenService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView, UserEntity userEntity){
        modelAndView.addObject("userEntity",userEntity);
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView,UserEntity userEntity){
        UserEntity existingUser = userTokenService.findByEmailIdIgnoreCase(userEntity.getEmailId());
        if(existingUser != null){
            modelAndView.addObject("message","This email already exists!");
            modelAndView.setViewName("error");
        }
         else {
             userTokenService.save(userEntity);

            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);

            confirmationTokenService.save(confirmationToken);

            String userEmail = userEntity.getEmailId();
            System.err.println("[/register value] confirmation token: " + confirmationToken.getConfirmationToken());
            System.err.println("user email: " + userEmail);

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("sadchill4@gmail.com");
            mailMessage.setText("To confirm yor account, please click here : "
                     + "http://localhost:5000/confirm?token=" + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            modelAndView.addObject("emailId",userEntity.getEmailId());

            modelAndView.setViewName("successfulRegistration");

        }

         return modelAndView;

    }

    @RequestMapping(value="/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){

        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);

        System.err.println("confirm-account-token: " + token);
        if (token != null){
            UserEntity userEntity = userTokenService.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
            userEntity.setEnabled(true);
            userTokenService.save(userEntity);
            modelAndView.setViewName("accountVerified");
        }
        else {
            modelAndView.addObject("message","the link is invalid broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
