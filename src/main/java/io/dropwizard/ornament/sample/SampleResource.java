package io.dropwizard.ornament.sample;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;

import io.dropwizard.ornament.ServiceConfiguration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;


/**
 * @author Federico Recio, Chris Wilson
 */
@Path("/sample")
@Api(value = "Sample resources for configured operations")
@Produces(MediaType.APPLICATION_JSON)
@SwaggerDefinition(securityDefinition = @SecurityDefinition(
    apiKeyAuthDefinitions = {@ApiKeyAuthDefinition(name = "Authorization", key = "apiKey",
        in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER)}))
public class SampleResource {

  private final ServiceConfiguration configuration;

  public SampleResource(ServiceConfiguration configuration) {
    this.configuration = configuration;
  }

  @GET
  @Timed
  @ApiOperation("Sample endpoint")
  public Response get() {
    return Response.ok(new SampleEntity("Foo-Bar", 1234)).build();
  }

  @GET
  @Timed
  @ApiOperation(value = "Sample endpoint *Authenticate with : Bearer 1234a",
      authorizations = {@Authorization("apiKey")})
  @Path("configured")
  public Response getconfigured() {
    return Response.ok(new SampleEntity(configuration.configuredProperty(), 1234)).build();
  }

  @GET
  @Timed
  @ExceptionMetered
  @ApiOperation("Sample endpoint with path param")
  @ApiResponses({@ApiResponse(code = 200, message = "Success")})
  @Path("/hello-with-path-param/{name}")
  public Response getWithPathParam(@PathParam("name") String name) {
    return Response.ok(new SampleEntity("Hello " + name, 333)).build();
  }

  @GET
  @Timed
  @ExceptionMetered
  @ApiOperation("Sample endpoint with query param")
  @Path("/hello-with-query-param")
  public Response getWithQueryParam(@QueryParam("name") Optional<String> name) {
    return Response.ok(new SampleEntity("Hello " + name.get(), 444)).build();
  }

  @POST
  @Timed
  @ExceptionMetered
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Get access token", notes = "Authenticate user and get a access token.",
      response = SampleEntity.class, authorizations = {@Authorization("apiKey")})
  public SampleEntity postFormToken(
      @FormParam("username") @ApiParam(defaultValue = "username") String username,
      @FormParam("password") @ApiParam(defaultValue = "password") String password) {
    return new SampleEntity(username, 1234);
  }

  @POST
  @Timed
  @ExceptionMetered
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Get access token", notes = "Authenticate user and get a access token.",
      response = SampleEntity.class, authorizations = {@Authorization("apiKey")})
  @Path("/json")
  public SampleEntity postJson(SampleEntity entity) {
    return new SampleEntity(entity.name(), entity.value());
  }

}
