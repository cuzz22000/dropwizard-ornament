package io.dropwizard.ornament.sample;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

public class SampleResourceTest extends BaseSampleResourceTest {

  @Test
  public void getTest() {
    Response response = target("/sample").request().accept(MediaType.APPLICATION_JSON).get();
    SampleEntity sampleEntity = response.readEntity(SampleEntity.class);
    SampleEntity expected = new SampleEntity("Foo-Bar", 1234);

    assertThat(response.getStatus(), is(200));
    assertThat(sampleEntity, is(equalTo(expected)));
  }

  @Test
  public void getConfiguredTest() {
    Response response =
        target("/sample/configured").request().accept(MediaType.APPLICATION_JSON).get();
    SampleEntity sampleEntity = response.readEntity(SampleEntity.class);
    SampleEntity expected = new SampleEntity(configuration.configuredProperty(), 1234);

    assertThat(response.getStatus(), is(200));
    assertThat(sampleEntity, is(equalTo(expected)));
  }

  @Test
  public void getWithPathParamTest() {
    Response response = target("/sample/hello-with-path-param/john").request()
        .accept(MediaType.APPLICATION_JSON).get();
    SampleEntity sampleEntity = response.readEntity(SampleEntity.class);
    SampleEntity expected = new SampleEntity("Hello john", 333);
    assertThat(response.getStatus(), is(200));
    assertThat(sampleEntity, is(equalTo(expected)));
  }

  @Test
  public void getWithQueryParamTest() {
    Response response = target("/sample/hello-with-query-param").queryParam("name", "john")
        .request().accept(MediaType.APPLICATION_JSON).get();
    SampleEntity sampleEntity = response.readEntity(SampleEntity.class);
    SampleEntity expected = new SampleEntity("Hello john", 444);
    assertThat(response.getStatus(), is(200));
    assertThat(sampleEntity, is(equalTo(expected)));

  }

  @Test
  public void postForTokenTest() {
    final Form form = new Form();
    form.param("username", "john").param("password", "1234");

    Response response = target("/sample").request()
        .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
    SampleEntity sampleEntity = response.readEntity(SampleEntity.class);
    SampleEntity expected = new SampleEntity("john", 1234);
    assertThat(response.getStatus(), is(200));
    assertThat(sampleEntity, is(equalTo(expected)));
  }

  @Test
  public void postJsonEntity() {
    final SampleEntity entity = new SampleEntity("some string", 12345);
    Response response = target("/sample/json").request().post(Entity.json(entity));
    assertThat(response.getStatus(), is(200));
  }


}
