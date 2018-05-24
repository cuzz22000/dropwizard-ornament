## Dropwizard Ornament

![alt travis](https://travis-ci.org/cuzz22000/dropwizard-ornament.svg?branch=master)

Provides base functionality for Dropwizard projects. Features extended onto the core functionality of Dropwizard with sample code, API Keys and Swagger integration. Developers need only to clone project and start implementing application specific functionality to launch a new project.

#### Docker
To run the demo use the following:

```bash
$> docker run -p 8080:8080 -p 8081:8081 cuzz22000/dropwizard-ornament:latest
```

#### Creating a new project

The script below requires [hub](https://github.com/github/hub).

```bash
# Provide proper substitutions elements wrapped in {....}
$ mkdir {project-name}
$ cd {project-name}
$ git init
$ hub create -p {githib-user}/{project-name}
$ echo -e '##{new-project}' > README.md
$ echo -e 'Name | Email | Mobile | Slack\n-----|-------|--------|------\n{your-name} | {email} | {cell-#} | {slack-username}' > OWNERS.md 
$ echo -e "rootProject.name='{project-name}'" > settings.gradle
$ git add -A
$ git commit -a -m ':new: initial'
$ git remote add source git@github.com:cuzz22000/dropwizard-ornament.git
$ git fetch source
$ git merge --squash --allow-unrelated-histories -s recursive -X ours remotes/source/master
$ git add -A
$ git commit -a -m 'merge with dropwizard-ornament'
$ git push --set-upstream origin master

```

#### Merging updates
If your project was not originally created using this project as a template you must add the project as a source.
 ```
[project-root] $ git remote add source git@github.com:cuzz22000/dropwizard-ornament.git
 ```
So that you don't merge all the updates it's recomended to use Git's [`cherry-pick`](http://git-scm.com/docs/git-cherry-pick) to select the relevant commit(s). 

```
[project-root] $ git fetch source
[project-root] $ git cherry-pick [commit]
```
Once the commit is merged the source will likely have conflicts use your favorite merge tool to resolve.

#### API Keys
A simple API authenticator is included for reference. All Sample Resources are secured with the key `foobar!`


#### Building
```bash
$ gradle
# running the dropwizard project
$ gradle run
```

#### Sample code.
Sample code is provided in package 

`io.dropwizard.ornament.sample`

 * `SampleResource.java` - `JAXRS` and `Swagger` annotated service endpoints
 * `SampleEnitity.java` - Simple valued entity

#### Logging
Logging is split into two separate for application and request. Logs are archived once a day up to five days, logs older than five days are removed.
Logging hierarchy:

```
# application
/var/log/app/current
# application archive where %d = timestamp
/var/log/app/arc-%d.log

# request
/var/log/request/current
# request archive where %d = timestamp
/var/log/request/arc-%d.log 

```
note: For local development it is recommended that you change the permission on the default logging directory. 

```bash
$ sudo chmod og+w /var/log
```

#### Swagger
[Swagger](http://swagger.io/) is initialized as part of the `ServiceApplication`. The sample resource has reverence [annotations](https://github.com/swagger-api/swagger-core/wiki/Annotations) for demonstration. The swagger documentation can be viewed once the application is running in your web browser -> [http://localhost:8080/swagger](http://localhost:8080/swagger)

To enable Swagger for your package update `configuration.yml`.
```
swagger:
  resourcePackage: io.dropwizard.ornament.sample,{your-package}
```

#### Resources
 * [Dropwizard](http://www.dropwizard.io/)
 * [Dropwizard Metrics](https://dropwizard.github.io/metrics/3.1.0/)
 * [Swagger](http://swagger.io/)
 * [Dropwizard Swagger](https://github.com/federecio/dropwizard-swagger)

