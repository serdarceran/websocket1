package netgloo.controllers;

import netgloo.Greeting;
import netgloo.HelloMessage;
import netgloo.Notification;
import netgloo.services.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class MainController {

  @Autowired
  private NotificationService notificationService;

  /**
   * GET  /  -> show the index page.
   */
  @RequestMapping("/")
  public String index() {
    return "index";
  }

  /**
   * GET  /notifications  -> show the notifications page.
   */
  @RequestMapping("/notifications")
  public String notifications() {
    return "notifications";
  }

  @RequestMapping("/send")
  public String send() {
    return "send";
  }

  /**
   * POST  /some-action  -> do an action.
   * 
   * After the action is performed will be notified UserA.
   */
  @RequestMapping(value = "/some-action", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<?> someAction(Principal principal) {


    // Do an action here
    // ...
    
    // Send the notification to "UserA" (by username)
    notificationService.notify(
      new Notification("hello " + principal.getName()), // notification object
            principal.getName()                    // username
    );
    
    // Return an http 200 status code
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @MessageMapping("/hello")
  @SendTo("/queue/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

} // class MainController
