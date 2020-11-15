package com.example.springbootsendactivationlink.controller;

import com.diogonunes.jcolor.AnsiFormat;
import com.example.springbootsendactivationlink.entity.ConfirmationToken;
import com.example.springbootsendactivationlink.entity.UserEntity;
import com.example.springbootsendactivationlink.service.ConfirmationTokenService;
import com.example.springbootsendactivationlink.service.UserTokenService;
import com.example.springbootsendactivationlink.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

@Controller
public class UserAccountController {
    //for printing color text
    AnsiFormat fInfo = new AnsiFormat(CYAN_TEXT());
    AnsiFormat fError = new AnsiFormat(YELLOW_TEXT(), RED_BACK());

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

        System.out.println(colorize("METHOD GET WORK [getting register]",fInfo ));
        return modelAndView;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ModelAndView registerUser(@Valid @ModelAttribute("userEntity") UserEntity userEntity,
                                     BindingResult bindingResult,
                                     ModelAndView modelAndView) {
        if(bindingResult.hasErrors()){
            System.out.println(colorize("BINDING RESULT ERROR", fError));
            bindingResult.getAllErrors().forEach(x -> System.out.println(colorize(x.toString(), fError)));

            modelAndView.addObject("userEntity",userEntity);
            modelAndView.setViewName("register");
            return modelAndView;
        }
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
            System.out.println(colorize("[/register value] confirmation token: " + confirmationToken.getConfirmationToken(), fInfo));
            System.out.println(colorize("user email: " + userEmail,fInfo));

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("sadchill4@gmail.com");
            mailMessage.setText("To confirm yor account, please click here : "
                     + "http://localhost:5000/confirm?token="
                     + confirmationToken.getConfirmationToken());

            emailService.sendEmail(mailMessage);

            modelAndView.addObject("emailId",userEntity.getEmailId());

            modelAndView.setViewName("successfulRegistration");

        }

         return modelAndView;

    }

    @RequestMapping(value="/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken){

        ConfirmationToken token = confirmationTokenService.findByConfirmationToken(confirmationToken);

        System.out.println(colorize("confirm-account-token: " + token,fInfo));

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
