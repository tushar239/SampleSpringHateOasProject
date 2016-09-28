package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

/*
https://spring.io/guides/gs/rest-hateoas/

@JsonCreator - signal on how Jackson can create an instance of this POJO
@JsonProperty - clearly marks what field Jackson should put this constructor argument into

ResourceSupport
---------------
To model the greeting representation, you create a resource representation class.
As the _links property is a fundamental property of the representation model, Spring HATEOAS ships with a base class ResourceSupport that allows you to add instances of Link
 */
public class Greeting extends ResourceSupport {

    private String content;

    /*
    Marker annotation that can be used to define constructors and factory
    methods as one to use for instantiating new instances of the associated class.
     */
    @JsonCreator
    public Greeting(@JsonProperty("content")String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}