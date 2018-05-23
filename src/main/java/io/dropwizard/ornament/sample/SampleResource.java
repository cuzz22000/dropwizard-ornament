package io.dropwizard.ornament.sample;

import java.security.Principal;

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

import io.dropwizard.auth.Auth;
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


@Path("/sample")
@Api(value = "Sample Operations *Authenticate with : \"Bearer foobar!\"")
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
  @ExceptionMetered
  @ApiOperation(value = "Sample Get Endpoint", authorizations = {@Authorization("apiKey")})
  public Response get(@ApiParam(hidden = true) @Auth Principal principal) {
    return Response.ok(new SampleEntity("White House", 2024561111)).build();
  }

  @GET
  @Timed
  @ExceptionMetered
  @ApiOperation(value = "Sample Get Endpoint with Path Param",
      authorizations = {@Authorization("apiKey")})
  @ApiResponses({@ApiResponse(code = 200, message = "Success")})
  @Path("/hello-with-path-param/{name}")
  public Response getWithPathParam(@PathParam("name") String name,
      @ApiParam(hidden = true) @Auth Principal principal) {
    return Response.ok(new SampleEntity("Hello " + name, 1234567)).build();
  }

  @GET
  @Timed
  @ExceptionMetered
  @ApiOperation(value = "Sample Get with Query Param", authorizations = {@Authorization("apiKey")})
  @Path("/hello-with-query-param")
  public Response getWithQueryParam(@QueryParam("name") Optional<String> name,
      @ApiParam(hidden = true) @Auth Principal principal) {
    return Response.ok(new SampleEntity("Hello " + name.get(), 444)).build();
  }

  @POST
  @Timed
  @ExceptionMetered
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @ApiOperation(value = "Sample Post Form Params",
      notes = "Authenticate user and get a access token.", response = SampleEntity.class,
      authorizations = {@Authorization("apiKey")})
  public SampleEntity postFormToken(
      @FormParam("username") @ApiParam(defaultValue = "first_name") String username,
      @FormParam("password") @ApiParam(defaultValue = "last_name") String password,
      @ApiParam(hidden = true) @Auth Principal principal) {
    return new SampleEntity(username, 1234);
  }

  @POST
  @Timed
  @ExceptionMetered
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Sample Post JSON Body", notes = "Echo Sample Entity",
      response = SampleEntity.class, authorizations = {@Authorization("apiKey")})
  @Path("/json")
  public SampleEntity postJson(final SampleEntity entity,
      @ApiParam(hidden = true) @Auth Principal principal) {
    return new SampleEntity(entity.name(), entity.value());
  }

}
