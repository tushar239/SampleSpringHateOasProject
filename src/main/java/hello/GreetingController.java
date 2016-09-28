package hello;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/*
https://spring.io/guides/gs/rest-hateoas/

ControllerLinkBuilder
---------------------
The most interesting part of the method implementation is how you create the link pointing to the controller method and how you add it to the representation model.

Both linkTo(…) and methodOn(…) are static methods on ControllerLinkBuilder that allow you to fake a method invocation on the controller.
The LinkBuilder returned will have inspected the controller method’s mapping annotation to build up exactly the URI the method is mapped to.

The call to withSelfRel() creates a Link instance that you add to the Greeting representation model.
 */


@Controller
public class GreetingController {

    private static final String TEMPLATE = "Hello, %s!";

    // http://localhost:8080/greeting

    @RequestMapping("/greeting")
    @ResponseBody
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
    }

    @RequestMapping("/greetings")
    @ResponseBody
    public List<Greeting> greetings(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        List<Greeting> greetings = new ArrayList<>();
        int size = 10;
        for (int i = 0; i < size; i++) {
            Greeting greeting = new Greeting(String.format(TEMPLATE, name + i));
            greeting.add(linkTo(methodOn(GreetingController.class).greeting(name + i)).withSelfRel());
            if(i < size-2) {
                greeting.add(linkTo(methodOn(GreetingController.class).greeting(name + (i + 1))).withRel(Link.REL_NEXT));
            }
            if(i > 0) {
                greeting.add(linkTo(methodOn(GreetingController.class).greeting(name + (i - 1))).withRel(Link.REL_PREVIOUS));
            }
            greeting.add(linkTo(methodOn(GreetingController.class).greeting(name + i)).withRel(Link.REL_FIRST));
            greeting.add(linkTo(methodOn(GreetingController.class).greeting(name + (size-1))).withRel(Link.REL_LAST));
            greetings.add(greeting);
        }
        return greetings;
    }
}
