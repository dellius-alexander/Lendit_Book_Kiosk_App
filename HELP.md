# Read Me First
The following was discovered as part of building this project:

* The JVM level was changed from '11' to '17', review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) on the wiki for more details.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.0-SNAPSHOT/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.0-SNAPSHOT/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.6.3/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)

---
---

# Some Helpful Things

## Merge MAIN with USER branch on a daily basis

```bash
git checkout <user branch name>      # gets you "on branch "
git fetch origin        # gets you up to date with origin
git merge origin/main

```

---
---

## Dos2Unix OR Unix2Dos

unix2dos is a tool to convert line breaks in a text file from Unix format (Line feed) to DOS format (carriage return + Line feed) `"\r"` and vice versa.

```bash
# dos2unix command : converts a DOS text file to UNIX format.
dos2unix <file_name>
# Unix2dos command : converts a Unix text file to DOS format
unix2dos <file_name>
```

---
---
## [Smart-Doc Maven Plugin](https://smart-doc-group.github.io/#/diy/config?id=allconfig)

smart-doc-maven-plugin is a maven plugin developed by the smart-doc official team. This plugin is available from smart-doc 1.7.9. Using smart-doc-maven-plugin makes it easier to integrate smart-doc into your project, and integration is more lightweight. You no longer need to write unit tests in your project to Start smart-doc to scan source code analysis and generate API documents. You can run the maven command directly or click on the preset goal of the smart-doc-maven-plugin in the IDE to generate API documentation. smart-doc-maven-plugin will also make smart-doc's ability to generate API documentation more powerful.

### ADD Plugin

```xml
<plugin>
    <groupId>com.github.shalousun</groupId>
    <artifactId>smart-doc-maven-plugin</artifactId>
    <version>[latest version]</version>
    <configuration>
        <!--skip option is used to disable plugin in child module-->
        <!--<skip>true</skip>-->
        <!--Specify the configuration file used to generate the document-->
        <configFile>./src/main/resources/smart-doc.json</configFile>
        <!--smart-doc implements automatic analysis of the dependency tree to load the source code of third-party dependencies. If some framework dependency libraries are not loaded, an error is reported, then use excludes to exclude-->
        <excludes>
            <!--The format is: groupId: artifactId; refer to the following-->
            <!--since 1.0.7 version you can also use regular matching to exclude, such as: poi. *-->
            <exclude>com.google.guava:guava</exclude>
        </excludes>
        <!--Since version 1.0.8, the plugin provides includes support-->
        <!--smart-doc can automatically analyze the dependency tree to load all dependent source code. In principle, it will affect the efficiency of document construction, so you can use includes to let the plugin load the components you configure.-->
        <includes>
            <!--The format is: groupId: artifactId; refer to the following-->
            <include>com.alibaba:fastjson</include>
        </includes>
    </configuration>
    <executions>
        <execution>
            <!--Comment out phase if you don't need to start smart-doc when compiling-->
            <phase>compile</phase>
            <goals>
                <!--smart-doc provides html, openapi, markdown, adoc and other goals-->
                <goal>html</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Create a json config

Create a json configuration file in your project. The smart-doc-maven-plugin plugin will use this configuration information. For example, create /src/main/resources/smart-doc.json in the project. The configuration contents are as follows.

```json
{
   "allInOne": true, // whether to merge documents into one file, generally recommended as true
   "isStrict": false,//If the strict mode is set to true, Smart-doc forces that the public method in each interface in the code has a comment.
   "outPath": "/src/main/resources" //Set the api document output path.
}
```
### ALLCONFIG

```json
{
  "serverUrl": "http://127.0.0.1", // Set the server address, not required
  "pathPrefix": "", //Set the path prefix,not required。eg: Servlet ContextPath
  "isStrict": false, // whether to enable strict mode
  "allInOne": true, // whether to merge documents into one file, generally recommended as true
  "outPath": "D: // md2", // Specify the output path of the document
  "coverOld": true, // Whether to overwrite old files, mainly used for mardown file overwrite
  "style":"xt256", //set highlight
  "createDebugPage": true,//Create a page that can be used to test your APIs like swagger
  "language":"ENGLISH",//support ENGLISH and CHINESE
  "packageFilters": "", // controller package filtering, multiple package names separated by commas
  "md5EncryptedHtmlName": false, // only used if each controller generates an html file
  "projectName": "smart-doc", // Configure your own project name
  "skipTransientField": true, // Not currently implemented
  "requestFieldToUnderline":true, //convert request field to underline
  "responseFieldToUnderline":true,//convert response field to underline
  "sortByTitle":false,//Sort by interface title, the default value is false
  "showAuthor":true,// display author,default is true
  "inlineEnum":true,// Set to true to display enumeration details in the parameter table
  "recursionLimit":7,// Set the number of recursive executions to avoid stack overflow, the default is 7
  "allInOneDocFileName":"index.html",//Customize the output document name
  "requestExample":"true",//Whether to display the request example in the document, the default value is true.
  "responseExample":"true",//Whether to display the response example in the document, the default is true.
  "requestParamsTable": true, //@since 2.2.5,Whether to display the request params table in the document, the default value is true.
  "responseParamsTable": true, //@since 2.2.5,Whether to display the response params table in the document, the default is true.
  "displayActualType": false,//display actual type of generic,
  "urlSuffix":".do",//Support the url suffix of the old SpringMVC project,@since 2.1.0
  "appKey": "xxx",// torna appKey, @since 2.0.9
  "appToken": "xxx", //torna appToken,@since 2.0.9
  "secret": "xx",//torna secret，@since 2.0.9
  "isReplace":true, //torna replace doc @since 2.2.4
  "openUrl": "torna server/api/",//torna server url,@since 2.0.9
  "tornaDebug":false, // show log while set true
  "ignoreRequestParams":[ //The request parameter object will be discarded when generating the document.@since 1.9.2
       "org.springframework.ui.ModelMap"
  ],
  "dataDictionaries": [{// Configure the data dictionary, no need to set
       "title": "Order Status", // The name of the data dictionary
       "enumClassName": "com.power.doc.enums.OrderEnum", // Data dictionary enumeration class name
       "codeField": "code", // The field name corresponding to the data dictionary dictionary code
       "descField": "desc" // Data dictionary object description information dictionary
  }],
  "errorCodeDictionaries": [{// error code list, no need to set
       "title": "title",
       "enumClassName": "com.power.doc.enums.ErrorCodeEnum", // Error code enumeration class
       "codeField": "code", // Code field name of the error code
       "descField": "desc" // Field name corresponding to the error code description
  }],
  "revisionLogs": [{// Set document change records, no need to set
       "version": "1.0", // Document version number
       "revisionTime": "2020-12-31 10:30", //revision time
       "author": "author", // Document change author
       "status": "update", // Change operation status, generally: create, update, etc.
       "remarks": "desc" // Change description
  }],
  "customResponseFields": [{// Customly add fields and comments. If api-doc encounters a field with the same name later, directly add a comment to the corresponding field. It is not necessary.
       "name": "code", // Override the response code field
       "desc": "Response code", // Override field comment of response code
       "value": "00000" // Set the value of the response code
  }],
  "customRequestFields": [{//@since 2.1.3
       "name":"code", //Override the request code field
       "desc":"request code", //Override field comment of response code
       "ownerClassName":"com.xxx.constant.entity.Result",
       "value":"200", // Set the value of the response code
       "required":true,
       "ignore":false
  }],
  "apiObjectReplacements": [{ // Supports replacing specified objects with Custom objects to complete document rendering
       "className": "org.springframework.data.domain.Pageable",
       "replacementClassName": "com.power.doc.model.PageRequestDto" //Use Custom PageRequestDto instead of JPA Pageable for document rendering.
  }],
  "rpcApiDependencies":[{ // Your Apache Dubbo api interface module dependency description.
       "artifactId":"SpringBoot2-Dubbo-Api",
       "groupId":"com.demo",
       "version":"1.0.0"
  }],
  "apiConstants": [{//Configure your own constant class, smart-doc automatically replaces with a specific value when parsing to a constant
       "constantsClassName": "com.power.doc.constants.RequestParamConstant"
   }],
  "responseBodyAdvice":{ //Support ResponseBodyAdvice
       "className":"com.power.common.model.CommonResult" // Standard POJO for Response
  },
  "requestBodyAdvice":{ //Support ResponseBodyAdvice
       "className":"com.power.common.model.CommonResult" // Standard POJO for Request
  },
  "rpcConsumerConfig": "src/main/resources/consumer-example.conf",//dubbo consumer config example
  "requestHeaders": [{// Set global request headers, no need to set
       "name": "token",
       "type": "string",
       "desc": "desc",
       "required": false,
       "pathPatterns": "*",
       "excludePathPatterns":"/app/page/**",
       "since": "-"
  }],
  "requestParams": [ //Public request parameters (a scenario where public request parameters are processed through interceptors) ，@since 2.2.3,no need to set
    {
      "name": "configPathParam",
      "type": "string",
      "desc": "desc",
      "paramIn": "path", // path,query
      "value":"testPath",//default is null
      "required": false,
      "since": "-",
      "pathPatterns": "*",
      "excludePathPatterns":"/app/page/**"
    },
    {
      "name": "configQueryParam",
      "type": "string",
      "desc": "desc",
      "paramIn": "query",
      "value":"testQuery",
      "required": false,
      "since": "-",
      "pathPatterns": "*",
      "excludePathPatterns":"/app/page/**"
    }
  ],
  "groups": [ // Group different controllers, @since 2.2.5
    {
      "name": "test group",
      "apis": "com.power.doc.controller.app.*"
    }
  ],
  "requestParamsTable": true, // Whether to display the request parameter table in the document, the default is true, @since 2.2.5
  "responseParamsTable": true // Whether to display the response parameter table in the document, the default is true, @since 2.2.5
}
```


---
---

## How to Change the Default Port in Spring Boot

1. Overview

    Spring Boot provides sensible defaults for many configuration properties. But we sometimes need to customize these with our case-specific values.

    And a common use case is changing the default port for the embedded server.

    In this quick tutorial, we'll cover several ways to achieve this.

2. Using `Property Files`

    The fastest and easiest way to customize Spring Boot is by overriding the values of the default properties.

    For the server port, the property we want to change is server.port.

    By default, the embedded server starts on port 8080.

    So, let's see how to provide a different value in an `application.properties` file:

    ```gradle
    server.port=8081
    ```

    Now the server will start on port 8081.

    And we can do the same if we're using an application.yml file:

    ```yaml
    server:
        port : 8081
    ```

    Both files are loaded automatically by Spring Boot if placed in the src/main/resources directory of a Maven application.

3. Environment-Specific Ports

    If we have an application deployed in different environments, we may want it to run on different ports on each system.

    We can easily achieve this by combining the property files approach with Spring profiles. Specifically, we can create a property file for each environment.

    For example, we'll have an `application-dev.properties` file with this content:

    ```gradle
    server.port=8081
    ```

    Then we'll add another `application-qa.properties` file with a different port:

    ```gradle
    server.port=8082
    ```

    Now, the property files configuration should be sufficient for most cases. However, there are other options for this goal, so let's explore them as well.

3. Programmatic Configuration

    We can configure the port programmatically either by setting the specific property when starting the application or by customizing the embedded server configuration.

    First, let's see how to set the property in the main 
    
    ```java
    @SpringBootApplication class:

    @SpringBootApplication
    public class CustomApplication {
        public static void main(String[] args) {
            SpringApplication app = new SpringApplication(CustomApplication.class);
            app.setDefaultProperties(Collections
            .singletonMap("server.port", "8083"));
            app.run(args);
        }
    }
    ```

    Next, to customize the server configuration, we have to implement the WebServerFactoryCustomizer interface:

    ```java
    @Component
    public class ServerPortCustomizer 
    implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
    
        @Override
        public void customize(ConfigurableWebServerFactory factory) {
            factory.setPort(8086);
        }
    }
    ```

    Note that this applies to the Spring Boot 2.x version.

    For Spring Boot 1.x, we can similarly implement the EmbeddedServletContainerCustomizer interface.

4. Using `Command-Line Arguments`
    When packaging and running our application as a jar, we can set the server.port argument with the java command:
    
    ```java
    java -jar spring-5.jar --server.port=8083
    ```

    or by using the equivalent syntax:

    ```java
    java -jar -Dserver.port=8083 spring-5.jar
    ```

5. Order of Evaluation

    As a final note, let's look at the order in which these approaches are evaluated by Spring Boot.

    Basically, the configurations priority is

    - embedded server configuration
    - command-line arguments
    - property files
    - main @SpringBootApplication configuration

---
---

## Java Multiline String Format

1. Text Blocks
    We can use Text Blocks by declaring the string with  `""" (three double-quote marks)`:
    
    It is, by far, the most convenient way to declare a multi-line string. Indeed, we don't have to deal with line separators or indentation spaces, as we can read in our dedicated article.

    **Note: `This feature is available in Java 15, but also Java 13 and 14 if we enable the preview feature`.**

    ```java
    public String textBlocks() {
        return """
            <table>
                <tr>
                    <th>Company</th>
                    <th>Contact</th>
                    <th>Country</th>
                </tr>
                <tr>
                    <td>Alfreds Futterkiste</td>
                    <td>Maria Anders</td>
                    <td> Germany</td>
                </tr>
                <tr>
                    <td>Centro comercial Moctezuma</td>
                    <td>Francisco Chang</td>
                    <td>Mexico</td>
                </tr>
            </table>""";
    }
    ```

2. Getting the Line Separator

    Each operating system can have its own way of defining and recognizing new lines. In Java, it's very easy to get the operating system line separator:

    ```java
    String newLine = System.getProperty("line.separator");
    ```

3. String Concatenation

    String concatenation is an easy native method which can be used to create multi-line strings:

    ```java
    public String stringConcatenation() {
        return "Get busy living"
                .concat(newLine)
                .concat("or")
                .concat(newLine)
                .concat("get busy dying.")
                .concat(newLine)
                .concat("--Stephen King");
    }
    ```

    Using the + operator is another way of achieving the same thing. Java compilers translate concat() and the + operator in the same way:

    ```java
    public String stringConcatenation() {
        return "Get busy living"
                + newLine
                + "or"
                + newLine
                + "get busy dying."
                + newLine
                + "--Stephen King";
    }
    ```

4. String Join

    Java 8 introduced String#join, which takes a delimiter along with some strings as arguments. It returns a final string having all input strings joined together with the delimiter:

    ```java
    public String stringJoin() {
        return String.join(newLine,
                        "Get busy living",
                        "or",
                        "get busy dying.",
                        "--Stephen King");
    }
    ```

5. String Builder

    StringBuilder is a helper class to build Strings. StringBuilder was introduced in Java 1.5 as a replacement for StringBuffer. It's a good choice for building huge strings in a loop:

    ```java
    public String stringBuilder() {
        return new StringBuilder()
                .append("Get busy living")
                .append(newLine)
                .append("or")
                .append(newLine)
                .append("get busy dying.")
                .append(newLine)
                .append("--Stephen King")
                .toString();
    }
    ```

6. String Writer

    StringWriter is another method that we can utilize to create a multi-line string. We don't need newLine here, because we use PrintWriter. The println function automatically adds new lines:

    ```java
    public String stringWriter() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("Get busy living");
        printWriter.println("or");
        printWriter.println("get busy dying.");
        printWriter.println("--Stephen King");
        return stringWriter.toString();
    }
    ```

7. Guava Joiner

    Using an external library just for a simple task like this doesn't make much sense, however, if the project already uses the library for other purposes, we can utilize it. For example, Google's Guava library is very popular. Guava has a Joiner class that is able to build multi-line strings:

    ```java
    public String guavaJoiner() {
        return Joiner.on(newLine).join(ImmutableList.of("Get busy living",
            "or",
            "get busy dying.",
            "--Stephen King"));
    }
    ```

9. Loading from a File

    Java reads files exactly as they are. This means that if we have a multi-line string in a text file, we'll have the same string when we read the file. There are a lot of ways to read from a file in Java.

    Actually, it's a good practice to separate long strings from code:

    Watch this new video for more information
    You may like this trending video
    
    ```java
    // You can also implement the IOException in a try{}catch{}finally block as well
    public String loadFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    ```

    and now pass the file in to a string variable

    ```java
    public String someFile = loadFromFile("Some_File_Path_&_Name_abs_or_relative")
    ```

---
---

<div id="bodyContent" class="mw-body">
		<div id="siteSub">From Git SCM Wiki</div>
		<div id="contentSub"></div>
		<div id="jump-to-nav" class="mw-jump">Jump to: <a href="#column-one">navigation</a>, <a href="#searchInput">search</a></div>
		<!-- start content -->
<div id="mw-content-text" lang="en" dir="ltr" class="mw-content-ltr"><h1> <span class="mw-headline" id="Git_Submodule_Tutorial"> Git Submodule Tutorial </span></h1>
<p>Submodule support has been available in Git since version 1.5.3. This tutorial explains how to create and publish a repository with four submodules using the <a rel="nofollow" class="external text" href="http://www.kernel.org/pub/software/scm/git/docs/git-submodule.html">git-submodule(1)</a> command.
</p><p>Submodules maintain their own identity; the submodule support just stores the submodule repository location and commit ID, so other developers who clone the superproject can easily clone all the submodules at the same revision.
</p><p>For the purposes of the tutorial, the public repositories will be published under your home directory in ~/subtut/public. Let's create the four public submodule repositories first:
</p>
<pre>$ mkdir -p ~/subtut/private
$ mkdir -p ~/subtut/public
$ cd ~/subtut/private
$ for mod in a b c d; do
    mkdir $mod
    cd $mod
    git init
    echo "module $mod" &gt; $mod.txt
    git add $mod.txt
    git commit -m "Initial commit, public module $mod"
    git clone --bare . ~/subtut/public/$mod.git
    git remote add origin ~/subtut/public/$mod.git
    git config branch.master.remote origin
    git config branch.master.merge refs/heads/master
    cd ..
done
</pre>
<p>Now create the public superproject; we won't actually add the submodules yet.
</p>
<pre>$ cd ~/subtut/private
$ mkdir super
$ cd super
$ git init
$ echo hi &gt; super.txt
$ git add super.txt
$ git commit -m "Initial commit of empty superproject"
$ git clone --bare . ~/subtut/public/super.git
$ git remote add origin ~/subtut/public/super.git
$ git config branch.master.remote origin
$ git config branch.master.merge refs/heads/master

</pre>
<p>Check out the superproject somewhere private and add all the submodules (note: it's important to give an absolute path for submodules on the local filesystem).
</p>
<pre>$ cd ~/subtut/private
$ cd super
$ for mod in a b c d; do git submodule add ~/subtut/public/$mod.git $mod; done
$ ls -a
.  ..  .git  .gitmodules  a  b  c  d  super.txt
</pre>
<p>The "git submodule add" command does a couple of things:
</p>
<ul><li> It clones the submodule under the current directory and by default checks out the master branch.
</li><li> It adds the submodule's clone path to the ".gitmodules" file and adds this file to the index, ready to be committed.
</li><li> It adds the submodule's current commit ID to the index, ready to be committed.
</li></ul>
<pre>$ cat .gitmodules
[submodule "a"]
        path = a
        url = /home/moses/subtut/public/a.git
[submodule "b"]
        path = b
        url = /home/moses/subtut/public/b.git
        ...
$ git status
# On branch master
# Changes to be committed:
#   (use "git reset HEAD &lt;file&gt;..." to unstage)
#
#       new file:   .gitmodules
#       new file:   a
#       new file:   b
#       new file:   c
#       new file:   d
#
</pre>
<p>Let's take a quick poke around one of the submodule checkouts.
</p>
<pre>$ cd a
$ ls -a
.  ..  .git  a.txt
$ git branch
* master
</pre>
<p>It looks just like a regular checkout:
</p>
<pre>$ cd ..
$ cat .git/config
        ...
        [remote "origin"]
        url = /home/moses/subtut/public/super.git
        fetch = +refs/heads/*:refs/remotes/origin/*
        ...
</pre>
<p>Commit the superproject and publish it:
</p>
<pre>$ git commit -m "Add submodules a, b, c, d."
Created commit fc7c350: Add submodules a, b, c, d.
 5 files changed, 16 insertions(+), 0 deletions(-)
 create mode 100644 .gitmodules
 create mode 160000 a
 create mode 160000 b
 create mode 160000 c
 create mode 160000 d
$ git push
$ git submodule init
</pre>
<p>Now look at it from the perspective of another developer:
</p>
<pre>$ mkdir ~/subtut/private2
$ cd&nbsp;!$
$ git clone ~/subtut/public/super.git
$ cd super
$ ls -a
.  ..  .git  .gitmodules  a  b  c  d  super.txt
</pre>
<p>The submodule directories are there, but they're empty:
</p>
<pre>$ ls -a a
.  ..
$ git submodule status
-d266b9873ad50488163457f025db7cdd9683d88b a
-e81d457da15309b4fef4249aba9b50187999670d b
-c1536a972b9affea0f16e0680ba87332dc059146 c
-d96249ff5d57de5de093e6baff9e0aafa5276a74 d
</pre>
<p>Pulling down the submodules is a two-step process. First run "git submodule init" to add the submodule repository URLs to .git/config:
</p>
<pre>$ git submodule init
$ git config -l
...
submodule.a.url=/home/moses/subtut/public/a.git
</pre>
<p>Now use "git submodule update" to clone the repositories and check out the commits specified in the superproject.
</p>
<pre>$ git submodule update
</pre>
<p>The submodule directories have been filled:
</p>
<pre>$ cd a
$ ls -a
.  ..  .git  a.txt
</pre>
<p>One major difference between "submodule update" and "submodule add" is that "update" checks out a specific commit, rather than the tip of a branch. It's like checking out a tag: the head is detached, so you're not working on a branch.
</p>
<pre>$ git branch
* (no branch)
  master
</pre>
<p>If you want to make a change within a submodule, you should first check out a branch, make your changes, publish the change within the submodule, and then update the superproject to reference the new commit:
</p>
<pre>$ git branch
* (no branch)
  master
$ git checkout master
$ echo "adding a line again" &gt;&gt; a.txt
$ git commit -a -m "Updated the submodule from within the superproject."
$ git push
$ cd ..
$ git add a        # There is a gotcha here.  Read about it below.
$ git commit -m "Updated submodule a."
$ git show
...
diff --git a/a b/a
index d266b98..261dfac 160000
--- a/a
+++ b/a
@@ -1 +1 @@
-Subproject commit d266b9873ad50488163457f025db7cdd9683d88b
+Subproject commit 261dfac35cb99d380eb966e102c1197139f7fa24
$ git submodule summary HEAD^
* a d266b98...261dfac (1):
  &gt; Updated the submodule from within the superproject.

$ git push
</pre>
<p>Switch back to the other private checkout; the new change should be visible.
</p>
<pre>$ cd ~/subtut/private/super
$ git pull
$ git submodule update
$ cat a/a.txt
module a
adding a line again
</pre>
<h2> <span class="mw-headline" id="Gotchas"> Gotchas </span></h2>
<p>If you use a forward slash (/) after the submodule name when adding  changes to a submodule and updating the container repository to use the latest submodule changes that you have pulled from the remote source:
</p>
<pre>$ git add submodule/
</pre>
<p>git will think you want to delete the submodule and want to add all the files in the submodule directory.  Please DONT use a forward slash after the submodule name.  You must type it like this:
</p>
<pre>$ git add submodule
</pre>
<p>Always publish the submodule change before publishing the change to the superproject that references it. If you forget to publish the submodule change, others won't be able to clone the repository:
</p>
<pre>$ echo i added another line to this file &gt;&gt; a.txt
$ git commit -a -m "doing it wrong this time"
$ cd ..
$ git add a
$ git commit -m "Updated submodule a again."
$ git push
$ cd ~/subtut/private2/super/
$ git pull
$ git submodule update
error: pathspec '261dfac35cb99d380eb966e102c1197139f7fa24' did not match any file(s) known to git.
Did you forget to 'git add'?
Unable to checkout '261dfac35cb99d380eb966e102c1197139f7fa24' in submodule path 'a'
</pre>
<p>It's not safe to run "git submodule update" if you've made changes within a submodule. They will be silently overwritten:
</p>
<pre>$ cat a.txt
module a
$ echo line added from private2 &gt;&gt; a.txt
$ git commit -a -m "line added inside private2"
$ cd ..
$ git submodule update
Submodule path 'a': checked out 'd266b9873ad50488163457f025db7cdd9683d88b'
$ cd a
$ cat a.txt
module a
</pre>
<p>The changes are still visible in the submodule's reflog:
</p>
<pre>$ git log -g --pretty=oneline
d266b9873ad50488163457f025db7cdd9683d88b HEAD@{0}: checkout: moving to d266b9873ad50488163457f025db7cdd9683d88b
4389b0d8e22e616c88a99ebd072cfebba40797ef HEAD@{1}: commit: line added inside private2
d266b9873ad50488163457f025db7cdd9683d88b HEAD@{2}: checkout: moving to d266b9873ad50488163457f025db7cdd9683d88b
</pre>
<h2> <span class="mw-headline" id="Removal"> Removal </span></h2>
<p>To remove a submodule you need to:
</p>
<ol><li> Delete the relevant line from the <i>.gitmodules</i> file.
</li><li> Delete the relevant section from <i>.git/config</i>.
</li><li> Run <i>git rm --cached path_to_submodule</i> (no trailing slash).
</li><li> Commit the superproject.
</li><li> Delete the now untracked submodule files.
</li></ol>
<p><br>
</p>
<!-- 
NewPP limit report
Preprocessor node count: 190/1000000
Post‐expand include size: 0/2097152 bytes
Template argument size: 0/2097152 bytes
Expensive parser function count: 0/100
-->

<!-- Saved in parser cache with key korg_mediawiki_git:pcache:idhash:102-0!*!*!!*!*!* and timestamp 20220211073521 -->
</div><div class="printfooter">
Retrieved from "<a href="https://git.wiki.kernel.org/index.php?title=GitSubmoduleTutorial&amp;oldid=19941">https://git.wiki.kernel.org/index.php?title=GitSubmoduleTutorial&amp;oldid=19941</a>"</div>
		<div id="catlinks" class="catlinks catlinks-allhidden"></div>		<!-- end content -->
				<div class="visualClear"></div>
	</div>

---
---

<div class="clearfix text-formatted field field--name-body field--type-text-with-summary field--label-hidden field__item"><p>We've gathered useful annotations developers use and packed them into a one-page Spring annotations cheat sheet. From basic annotations you need to get your project started, to service discovery annotations, we’ve got you covered.</p><p><b>Related reading:</b> How many developers are actually using Spring? Find out in our <a data-entity-substitution="canonical" data-entity-type="node" data-entity-uuid="d50a41a8-3e73-4080-ab14-0ad6ade27296" href="/resources/java-developer-productivity-report-2021">2021 Java Developer Productivity Report.</a></p><h2 id="spring-annotations">Important Spring Annotations</h2><p>Here are the most important annotations any Java developer working with Spring should know:</p><ul><li><strong>@Configuration</strong>&nbsp;- used to mark a class as a source of the bean definitions. Beans are the components of the system that you want to wire together. A method marked with the @Bean annotation is a bean producer. Spring will handle the life cycle of the beans for you, and it will use these methods to create the beans.</li><li><strong>@ComponentScan</strong> -use to make sure that Spring knows about your configuration classes and can initialize the beans correctly. It makes Spring scan the packages configured with it for the @Configuration classes.</li><li><strong>@Import -</strong>&nbsp; If you need even more precise control of the configuration classes, you can always use @import&nbsp;&nbsp;to load additional configuration. This one works even when you specify the beans in an XML file like it's 1999.</li><li><strong>@Component</strong>&nbsp;- Another way to declare a bean is to mark a class with a @Component annotation.&nbsp;Doing this turns the class into a Spring bean at the auto-scan time.</li><li><strong>@Service -</strong> Mark a specialization of a @Component.&nbsp;It tells Spring that it's safe to manage them with more freedom than regular components. Remember, services have no encapsulated state.</li><li><strong>@Autowired -&nbsp;</strong>To wire the application parts together, use the @Autowired&nbsp;on the fields, constructors, or methods in a component. Spring's dependency injection mechanism wires appropriate beans into the class members marked with @Autowired.</li><li><strong>@Bean -&nbsp;</strong>A method-level annotation to specify a returned bean to be managed by Spring context. The returned bean has the same name as the factory method.</li><li><strong>@Lookup -&nbsp;</strong>tells Spring to return an instance of the method's return type when we invoke it.</li><li><strong>@Primary -&nbsp;</strong>gives higher preference to a bean when there are multiple beans of the same type.</li><li><strong>@Required -&nbsp;</strong>shows that the setter method must be configured to be dependency-injected with a value at configuration time. Use @Required on setter methods to mark dependencies populated through XML. Otherwise, a BeanInitializationException is thrown.</li><li><strong>@Value -&nbsp;</strong>used to assign values into fields in Spring-managed beans. It's compatible with the constructor, setter, and field injection.</li><li><strong>@DependsOn -&nbsp;</strong>makes Spring initialize other beans before the annotated one. Usually, this behavior is automatic, based on the explicit dependencies between beans. The @DependsOn annotation may be used on any class directly or indirectly annotated with @Component or on methods annotated with @Bean.</li><li><strong>@Lazy -&nbsp;</strong>makes beans to initialize lazily. @Lazy annotation may be used on any class directly or indirectly annotated with @Component or on methods annotated with @Bean.</li><li><strong>@Scope -&nbsp;</strong>used to define the scope of a @Component class or a @Bean definition and can be either singleton, prototype, request, session, globalSession, or custom scope.</li><li><strong>@Profile -&nbsp;</strong>adds beans to the application only when that profile is active.</li></ul><p>Armed with these annotations you can make the application come together with a very little effort. Naturally, there are more Spring annotations that you might want to use, but these here are the core of the framework that enables the flexibility Spring is known for! <a name="spring-web" id="spring-web"></a></p><blockquote>Looking to save time on your Java development? <a href="https://www.jrebel.com/products/jrebel/free-trial">Try JRebel free for 10 days.</a></blockquote><h2 id="spring-boot-annotations">Important Spring Boot Annotations</h2><p>Let's look at some of the most frequently used annotations in the context of web apps. Most of our readers are either backend engineers or are doing full stack developer jobs. So it makes sense to popularize the Spring Framework annotations that make web development easier.</p><h3>@SpringBootApplication</h3><p>One of the most basic and helpful annotations, is <strong>@SpringBootApplication</strong>. It's syntactic sugar for combining other annotations that we'll look at in just a moment. @SpringBootApplication is <strong>@Configuration</strong>, <strong>@EnableAutoConfiguration</strong> and <strong>@ComponentScan</strong> annotations combined, configured with their default attributes.</p><h3>@Configuration and @ComponentScan</h3><p>The @Configuration and @ComponentScan annotations that we described above make Spring create and configure the beans and components of your application. It's a great way to decouple the actual business logic code from wiring the app together.</p><h3>@EnableAutoConfiguration</h3><p>Now the <strong>@EnableAutoConfiguration</strong> annotation is even better. It makes Spring guess the configuration based on the JAR files available on the classpath. It can figure out what libraries you use and preconfigure their components without you lifting a finger. It is how all the spring-boot-starter libraries work. Meaning it's a major lifesaver both when you're just starting to work with a library as well as when you know and trust the default config to be reasonable.</p><h2 id="spring-boot-web-annotations">Important Spring MVC Web Annotations</h2><p>The following annotations make Spring configure your app to be a web application, capable of serving the HTTP response.</p><ul><li><strong>@Controller -&nbsp;</strong>marks the class as a web controller, capable of handling the HTTP requests. Spring will look at the methods of the class marked with the @Controller annotation and establish the routing table to know which methods serve which endpoints.</li><li><strong>@ResponseBody - </strong>The @ResponseBody is a utility annotation that makes Spring bind a method's return value to the HTTP response body. When building a JSON endpoint, this is an amazing way to magically convert your objects into JSON for easier consumption.</li><li><strong>@RestController - </strong>Then there's the @RestController annotation, a convenience syntax for @Controller and @ResponseBody together. This means that all the action methods in the marked class will return the JSON response.</li><li><strong>@RequestMapping(method = RequestMethod.GET, value = "/path") - </strong>The @RequestMapping(method = RequestMethod.GET, value = "/path") annotation specifies a method in the controller that should be responsible for serving the HTTP request to the given path. Spring will work the implementation details of how it's done. You simply specify the path value on the annotation and Spring will route the requests into the correct action methods.</li><li><strong>@RequestParam(value="name", defaultValue="World") - </strong> Naturally, the methods handling the requests might take parameters. To help you with binding the HTTP parameters into the action method arguments, you can use the @RequestParam(value="name", defaultValue="World") annotation. Spring will parse the request parameters and put the appropriate ones into your method arguments.</li><li><strong>@PathVariable("placeholderName") - </strong>Another common way to provide information to the backend is to encode it in the URL. Then you can use the @PathVariable("placeholderName") annotation to bring the values from the URL to the method arguments. <a name="spring-cloud" id="spring-cloud"></a></li></ul><h2>Final Thoughts</h2><p>In this post, we've looked at many annotations that a Java developer should know if they want to use the Spring Framework. We've covered the most frequently used and perhaps the most important annotations—from those that enable dependency injection for your components to&nbsp;the ways to bind your code to respond to HTTP requests.</p><h3 id="spring-annotations-cheat-sheet">Download the Spring Annotations Cheat Sheet</h3><p>Ready to download our one-page Spring Annotations cheat sheet pdf? Click the button below to get started!</p><p><span class="btn--blue-default"><a href="./images/cheatsheet-jrebel-spring-annotations.pdf">Get the Cheat Sheet</a></span></p><p><span id="cke_bm_65S" style="display: none;">&nbsp;</span></p><a href="./images/cheatsheet-jrebel-spring-annotations.pdf"><div data-embed-button="image_embed" data-entity-embed-display="view_mode:media.embed" data-entity-type="media" data-entity-uuid="70bf80ae-d956-4a6e-a3d4-ce62dea9a82b" data-langcode="en" class="embedded-entity image_embed"><article class="media media--type-image media--view-mode-embed"><div class="field field--name-thumbnail field--type-image field--label-hidden field__item"><img data-src="./images/image-web-cheatsheet-jrebel-spring-annotations.png" width="600" height="424" alt="Spring Annotations Cheat Sheet — Preview Image" loading="lazy" typeof="foaf:Image" class="lozad lazyload"></div></article></div></a><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><h3>Additional Resources</h3><p>If you're looking for additional Java cheat sheets, be sure to check out our <a href="https://www.jrebel.com/resources/java-resources">Java cheat sheet</a> collection. It's packed full of cheat sheets and shortcuts for popular Java technologies.</p><h3>Try JRebel</h3><p>Want to see how much time JRebel can save you?&nbsp;Try it free for 10 days with a JRebel trial.</p><p><span class="btn--blue-ghost"><span class="btn--blue-default"><a href="https://www.jrebel.com/products/jrebel/free-trial" tabindex="-1">TRY JREBEL FOR FREE</a></span></span></p><p><i>Note: This post was originally published on October 26, 2016 and has been updated for accuracy and comprehensiveness. </i></p></div>
<div class="printfooter">
Retrieved from "<a href="https://www.jrebel.com/blog/spring-annotations-cheat-sheet&amp;oldid=19941">https://www.jrebel.com/blog/spring-annotations-cheat-sheet&amp;oldid=19941</a>"</div>
 
 
---
---

<h2 ><a name="Maven_CLI_Options_Reference" href="https://maven.apache.org/ref/3.2.3/maven-embedder/cli.html">Maven CLI Options Reference</a></h2>

```bash
usage: mvn [options] [<goal(s)>] [<phase(s)>]
```
<table border="1" class="zebra-striped"><tbody><tr class="a"><th><b>Options</b></th><th><b>Description</b></th></tr><tr class="b"><td><code>-<a name="am">am</a>,--<a name="also-make">also-make</a></code></td><td>If project list is specified, also build projects required by the list</td></tr>
<tr class="a"><td><code>-<a name="amd">amd</a>,--<a name="also-make-dependents">also-make-dependents</a></code></td><td>If project list is specified, also build projects that depend on projects on the list</td></tr>
<tr class="b"><td><code>-<a name="B">B</a>,--<a name="batch-mode">batch-mode</a></code></td><td>Run in non-interactive (batch) mode</td></tr>
<tr class="a"><td><code>-<a name="b">b</a>,--<a name="builder">builder</a> &lt;arg&gt;</code></td><td>The id of the build strategy to use.</td></tr>
<tr class="b"><td><code>-<a name="C">C</a>,--<a name="strict-checksums">strict-checksums</a></code></td><td>Fail the build if checksums don't match</td></tr>
<tr class="a"><td><code>-<a name="c">c</a>,--<a name="lax-checksums">lax-checksums</a></code></td><td>Warn if checksums don't match</td></tr>
<tr class="b"><td><code>-<a name="cpu">cpu</a>,--<a name="check-plugin-updates">check-plugin-updates</a></code></td><td>Ineffective, only kept for backward compatibility</td></tr>
<tr class="a"><td><code>-<a name="D">D</a>,--<a name="define">define</a> &lt;arg&gt;</code></td><td>Define a system property</td></tr>
<tr class="b"><td><code>-<a name="e">e</a>,--<a name="errors">errors</a></code></td><td>Produce execution error messages</td></tr>
<tr class="a"><td><code>-<a name="emp">emp</a>,--<a name="encrypt-master-password">encrypt-master-password</a> &lt;arg&gt;</code></td><td>Encrypt master security password</td></tr>
<tr class="b"><td><code>-<a name="ep">ep</a>,--<a name="encrypt-password">encrypt-password</a> &lt;arg&gt;</code></td><td>Encrypt server password</td></tr>
<tr class="a"><td><code>-<a name="f">f</a>,--<a name="file">file</a> &lt;arg&gt;</code></td><td>Force the use of an alternate POM file (or directory with pom.xml).</td></tr>
<tr class="b"><td><code>-<a name="fae">fae</a>,--<a name="fail-at-end">fail-at-end</a></code></td><td>Only fail the build afterwards; allow all non-impacted builds to continue</td></tr>
<tr class="a"><td><code>-<a name="ff">ff</a>,--<a name="fail-fast">fail-fast</a></code></td><td>Stop at first failure in reactorized builds</td></tr>
<tr class="b"><td><code>-<a name="fn">fn</a>,--<a name="fail-never">fail-never</a></code></td><td>NEVER fail the build, regardless of project result</td></tr>
<tr class="a"><td><code>-<a name="gs">gs</a>,--<a name="global-settings">global-settings</a> &lt;arg&gt;</code></td><td>Alternate path for the global settings file</td></tr>
<tr class="b"><td><code>-<a name="h">h</a>,--<a name="help">help</a></code></td><td>Display help information</td></tr>
<tr class="a"><td><code>-<a name="l">l</a>,--<a name="log-file">log-file</a> &lt;arg&gt;</code></td><td>Log file to where all build output will go.</td></tr>
<tr class="b"><td><code>-<a name="llr">llr</a>,--<a name="legacy-local-repository">legacy-local-repository</a></code></td><td>Use Maven 2 Legacy Local Repository behaviour, ie no use of _remote.repositories. Can also be activated by using -Dmaven.legacyLocalRepo=true</td></tr>
<tr class="a"><td><code>-<a name="N">N</a>,--<a name="non-recursive">non-recursive</a></code></td><td>Do not recurse into sub-projects</td></tr>
<tr class="b"><td><code>-<a name="npr">npr</a>,--<a name="no-plugin-registry">no-plugin-registry</a></code></td><td>Ineffective, only kept for backward compatibility</td></tr>
<tr class="a"><td><code>-<a name="npu">npu</a>,--<a name="no-plugin-updates">no-plugin-updates</a></code></td><td>Ineffective, only kept for backward compatibility</td></tr>
<tr class="b"><td><code>-<a name="nsu">nsu</a>,--<a name="no-snapshot-updates">no-snapshot-updates</a></code></td><td>Suppress SNAPSHOT updates</td></tr>
<tr class="a"><td><code>-<a name="o">o</a>,--<a name="offline">offline</a></code></td><td>Work offline</td></tr>
<tr class="b"><td><code>-<a name="P">P</a>,--<a name="activate-profiles">activate-profiles</a> &lt;arg&gt;</code></td><td>Comma-delimited list of profiles to activate</td></tr>
<tr class="a"><td><code>-<a name="pl">pl</a>,--<a name="projects">projects</a> &lt;arg&gt;</code></td><td>Comma-delimited list of specified reactor projects to build instead of all projects. A project can be specified by [groupId]:artifactId or by its relative path.</td></tr>
<tr class="b"><td><code>-<a name="q">q</a>,--<a name="quiet">quiet</a></code></td><td>Quiet output - only show errors</td></tr>
<tr class="a"><td><code>-<a name="rf">rf</a>,--<a name="resume-from">resume-from</a> &lt;arg&gt;</code></td><td>Resume reactor from specified project</td></tr>
<tr class="b"><td><code>-<a name="s">s</a>,--<a name="settings">settings</a> &lt;arg&gt;</code></td><td>Alternate path for the user settings file</td></tr>
<tr class="a"><td><code>-<a name="T">T</a>,--<a name="threads">threads</a> &lt;arg&gt;</code></td><td>Thread count, for instance 2.0C where C is core multiplied</td></tr>
<tr class="b"><td><code>-<a name="t">t</a>,--<a name="toolchains">toolchains</a> &lt;arg&gt;</code></td><td>Alternate path for the user toolchains file</td></tr>
<tr class="a"><td><code>-<a name="U">U</a>,--<a name="update-snapshots">update-snapshots</a></code></td><td>Forces a check for missing releases and updated snapshots on remote repositories</td></tr>
<tr class="b"><td><code>-<a name="up">up</a>,--<a name="update-plugins">update-plugins</a></code></td><td>Ineffective, only kept for backward compatibility</td></tr>
<tr class="a"><td><code>-<a name="V">V</a>,--<a name="show-version">show-version</a></code></td><td>Display version information WITHOUT stopping build</td></tr>
<tr class="b"><td><code>-<a name="v">v</a>,--<a name="version">version</a></code></td><td>Display version information</td></tr>
<tr class="a"><td><code>-<a name="X">X</a>,--<a name="debug">debug</a></code></td><td>Produce execution debug output</td></tr>
</tbody></table>


---
---


<strong>HTML5 Tags</strong><span class="rounded toggle">Order by Category</span>


<p>The following section contains a complete list of standard tags belonging to the latest HTML5 and XHTML 1.1 specifications. All the tags are listed alphabetically.</p>
<div class="shadow">
<table class="data">
<thead>
<tr>
    <th style="width: 130px;">Tag</th>
    <th>Description</th>
</tr>
</thead>
<tbody>
<tr>
    <td><a href="html-a-tag.php" target="_blank"><code>&lt;a&gt;</code></a></td>
    <td>Defines a hyperlink.</td>
</tr>
<tr>
    <td><a href="html-abbr-tag.php" target="_blank"><code>&lt;abbr&gt;</code></a></td>
    <td>Defines an abbreviated form of a longer word or phrase.</td>
</tr>
<tr>
    <td><a href="html-acronym-tag.php" target="_blank" class="obsolete-tag"><code>&lt;acronym&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Defines an acronym. Use <a href="html-abbr-tag.php" target="_blank"><code>&lt;abbr&gt;</code></a> instead.</td>
</tr>
<tr>
    <td><a href="html-address-tag.php" target="_blank"><code>&lt;address&gt;</code></a></td>
    <td>Specifies the author's contact information.</td>
</tr>
<tr>
    <td><a href="html-applet-tag.php" target="_blank" class="obsolete-tag"><code>&lt;applet&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Embeds a Java applet (mini Java applications) on the page. Use <a href="html-object-tag.php" target="_blank"><code>&lt;object&gt;</code></a> instead.</td>
</tr>
<tr>
    <td><a href="html-area-tag.php" target="_blank"><code>&lt;area&gt;</code></a></td>
    <td>Defines a specific area within an image map.</td>
</tr>
<tr>
    <td><a href="html5-article-tag.php" target="_blank"><code>&lt;article&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines an article.</td>
</tr>
<tr>
    <td><a href="html5-aside-tag.php" target="_blank"><code>&lt;aside&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines some content loosely related to the page content.</td>
</tr>
<tr>
    <td><a href="html5-audio-tag.php" target="_blank"><code>&lt;audio&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Embeds a sound, or an audio stream in an HTML document.</td>
</tr>
<tr>
    <td><a href="html-b-tag.php" target="_blank"><code>&lt;b&gt;</code></a></td>
    <td>Displays text in a bold style.</td>
</tr>
<tr>
    <td><a href="html-base-tag.php" target="_blank"><code>&lt;base&gt;</code></a></td>
    <td>Defines the base URL for all relative URLs in a document.</td>
</tr>
<tr>
    <td><a href="html-basefont-tag.php" target="_blank" class="obsolete-tag"><code>&lt;basefont&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Specifies the base font for a page. Use CSS instead.</td>
</tr>
<tr>
    <td><a href="html5-bdi-tag.php" target="_blank"><code>&lt;bdi&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents text that is isolated from its surrounding for the purposes of bidirectional text formatting.</td>
</tr>
<tr>
    <td><a href="html-bdo-tag.php" target="_blank"><code>&lt;bdo&gt;</code></a></td>
    <td>Overrides the current text direction.</td>
</tr>
<tr>
    <td><a href="html-big-tag.php" target="_blank" class="obsolete-tag"><code>&lt;big&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Displays text in a large size. Use CSS instead.</td>
</tr>
<tr>
    <td><a href="html-blockquote-tag.php" target="_blank"><code>&lt;blockquote&gt;</code></a></td>
    <td>Represents a section that is quoted from another source.</td>
</tr>								
<tr>
    <td><a href="html-body-tag.php" target="_blank"><code>&lt;body&gt;</code></a></td>
    <td>Defines the document's body.</td>
</tr>
<tr>
    <td><a href="html-br-tag.php" target="_blank"><code>&lt;br&gt;</code></a></td>
    <td>Produces a single line break.</td>
</tr>
<tr>
    <td><a href="html-button-tag.php" target="_blank"><code>&lt;button&gt;</code></a></td>
    <td>Creates a clickable button.</td>
</tr>
<tr>
    <td><a href="html5-canvas-tag.php" target="_blank"><code>&lt;canvas&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a region in the document, which can be used to draw graphics on the fly via scripting (usually JavaScript).</td>
</tr>
<tr>
    <td><a href="html-caption-tag.php" target="_blank"><code>&lt;caption&gt;</code></a></td>
    <td>Defines the caption or title of the table.</td>
</tr>
<tr>
    <td><a href="html-center-tag.php" target="_blank" class="obsolete-tag"><code>&lt;center&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a>  Align contents in the center. Use CSS instead.</td>
</tr>
<tr>
    <td><a href="html-cite-tag.php" target="_blank"><code>&lt;cite&gt;</code></a></td>
    <td>Indicates a citation or reference to another source.</td>
</tr>
<tr>
    <td><a href="html-code-tag.php" target="_blank"><code>&lt;code&gt;</code></a></td>
    <td>Specifies text as computer code.</td>
</tr>
<tr>
    <td><a href="html-col-tag.php" target="_blank"><code>&lt;col&gt;</code></a></td>
    <td>Defines attribute values for one or more columns in a table.</td>
</tr>
<tr>
    <td><a href="html-colgroup-tag.php" target="_blank"><code>&lt;colgroup&gt;</code></a></td>
    <td>Specifies attributes for multiple columns in a table.</td>
</tr>
<tr>
    <td><a href="html5-data-tag.php" target="_blank"><code>&lt;data&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Links a piece of content with a machine-readable translation.</td>
</tr>
<tr>
    <td><a href="html5-datalist-tag.php" target="_blank"><code>&lt;datalist&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a set of pre-defined options for an <a href="html-input-tag.php" target="_blank"><code>&lt;input&gt;</code></a> element.</td>
</tr>
<tr>
    <td><a href="html-dd-tag.php" target="_blank"><code>&lt;dd&gt;</code></a></td>
    <td>Specifies a description, or value for the term (<a href="html-dt-tag.php" target="_blank"><code>&lt;dt&gt;</code></a>) in a description list (<a href="html-dl-tag.php" target="_blank"><code>&lt;dl&gt;</code></a>).</td>
</tr>
<tr>
    <td><a href="html-del-tag.php" target="_blank"><code>&lt;del&gt;</code></a></td>
    <td>Represents text that has been deleted from the document.</td>
</tr>
<tr>
    <td><a href="html5-details-tag.php" target="_blank"><code>&lt;details&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a widget from which the user can obtain additional information or controls on-demand.</td>
</tr>
<tr>
    <td><a href="html-dfn-tag.php" target="_blank"><code>&lt;dfn&gt;</code></a></td>
    <td>Specifies a definition.</td>
</tr>
<tr>
    <td><a href="html5-dialog-tag.php" target="_blank"><code>&lt;dialog&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a dialog box or subwindow.</td>
</tr>
<tr>
    <td><a href="html-dir-tag.php" target="_blank" class="obsolete-tag"><code>&lt;dir&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Defines a directory list. Use <a href="html-ul-tag.php" target="_blank"><code>&lt;ul&gt;</code></a> instead.</td>
</tr>
<tr>
    <td><a href="html-div-tag.php" target="_blank"><code>&lt;div&gt;</code></a></td>
    <td>Specifies a division or a section in a document.</td>
</tr>
<tr>
    <td><a href="html-dl-tag.php" target="_blank"><code>&lt;dl&gt;</code></a></td>
    <td>Defines a description list.</td>
</tr>
<tr>
    <td><a href="html-dt-tag.php" target="_blank"><code>&lt;dt&gt;</code></a></td>
    <td>Defines a term (an item) in a description list.</td>
</tr>
<tr>
    <td><a href="html-em-tag.php" target="_blank"><code>&lt;em&gt;</code></a></td>
    <td>Defines emphasized text.</td>
</tr>
<tr>
    <td><a href="html5-embed-tag.php" target="_blank"><code>&lt;embed&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Embeds external application, typically multimedia content like audio or video into an HTML document.</td>
</tr>
<tr>
    <td><a href="html-fieldset-tag.php" target="_blank"><code>&lt;fieldset&gt;</code></a></td>
    <td>Specifies a set of related form fields.</td>
</tr>
<tr>
    <td><a href="html5-figcaption-tag.php" target="_blank"><code>&lt;figcaption&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a caption or legend for a figure.</td>
</tr>
<tr>
    <td><a href="html5-figure-tag.php" target="_blank"><code>&lt;figure&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a figure illustrated as part of the document.</td>
</tr>
<tr>
    <td><a href="html-font-tag.php" target="_blank" class="obsolete-tag"><code>&lt;font&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Defines font, color, and size for text. Use CSS instead.</td>
</tr>
<tr>
    <td><a href="html5-footer-tag.php" target="_blank"><code>&lt;footer&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents the footer of a document or a section.</td>
</tr>
<tr>
    <td><a href="html-form-tag.php" target="_blank"><code>&lt;form&gt;</code></a></td>
    <td>Defines an HTML form for user input.</td>
</tr>
<tr>
    <td><a href="html-frame-tag.php" target="_blank" class="obsolete-tag"><code>&lt;frame&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Defines a single frame within a frameset.</td>
</tr>
<tr>
    <td><a href="html-frameset-tag.php" target="_blank" class="obsolete-tag"><code>&lt;frameset&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Defines a collection of frames or other frameset.</td>
</tr>
<tr>
    <td><a href="html-head-tag.php" target="_blank"><code>&lt;head&gt;</code></a></td>
    <td>Defines the head portion of the document that contains information about the document such as title.</td>
</tr>
<tr>
    <td><a href="html5-header-tag.php" target="_blank"><code>&lt;header&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents the header of a document or a section.</td>
</tr>
<tr>
    <td><a href="html5-hgroup-tag.php" target="_blank"><code>&lt;hgroup&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a group of headings.</td>
</tr>
<tr>
    <td><a href="html-headings-tag.php" target="_blank"><code>&lt;h1&gt; to &lt;h6&gt;</code></a></td>
    <td>Defines HTML headings.</td>
</tr>
<tr>
    <td><a href="html-hr-tag.php" target="_blank"><code>&lt;hr&gt;</code></a></td>
    <td>Produce a horizontal line.</td>
</tr>
<tr>
    <td><a href="html-html-tag.php" target="_blank"><code>&lt;html&gt;</code></a></td>
    <td>Defines the root of an HTML document.</td>
</tr>
<tr>
    <td><a href="html-i-tag.php" target="_blank"><code>&lt;i&gt;</code></a></td>
    <td>Displays text in an italic style.</td>
</tr>
<tr>
    <td><a href="html-iframe-tag.php" target="_blank"><code>&lt;iframe&gt;</code></a></td>
    <td>Displays a URL in an inline frame.</td>
</tr>
<tr>
    <td><a href="html-img-tag.php" target="_blank"><code>&lt;img&gt;</code></a></td>
    <td>Represents an image.</td>
</tr>
<tr>
    <td><a href="html-input-tag.php" target="_blank"><code>&lt;input&gt;</code></a></td>
    <td>Defines an input control.</td>
</tr>
<tr>
    <td><a href="html-ins-tag.php" target="_blank"><code>&lt;ins&gt;</code></a></td>
    <td>Defines a block of text that has been inserted into a document.</td>
</tr>
<tr>
    <td><a href="html-kbd-tag.php" target="_blank"><code>&lt;kbd&gt;</code></a></td>
    <td>Specifies text as keyboard input.</td>
</tr>
<tr>
    <td><a href="html5-keygen-tag.php" target="_blank"><code>&lt;keygen&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a control for generating a public-private key pair.</td>
</tr>
<tr>
    <td><a href="html-label-tag.php" target="_blank"><code>&lt;label&gt;</code></a></td>
    <td>Defines a label for an <code>&lt;input&gt;</code> control.</td>
</tr>
<tr>
    <td><a href="html-legend-tag.php" target="_blank"><code>&lt;legend&gt;</code></a></td>
    <td>Defines a caption for a <code>&lt;fieldset&gt;</code> element.</td>
</tr>
<tr>
    <td><a href="html-li-tag.php" target="_blank"><code>&lt;li&gt;</code></a></td>
    <td>Defines a list item.</td>
</tr>
<tr>
    <td><a href="html-link-tag.php" target="_blank"><code>&lt;link&gt;</code></a></td>
    <td>Defines the relationship between the current document and an external resource.</td>
</tr>
<tr>
    <td><a href="html5-main-tag.php" target="_blank"><code>&lt;main&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents the main or dominant content of the document.</td>
</tr>
<tr>
    <td><a href="html-map-tag.php" target="_blank"><code>&lt;map&gt;</code></a></td>
    <td>Defines a client-side image-map.</td>
</tr>
<tr>
    <td><a href="html5-mark-tag.php" target="_blank"><code>&lt;mark&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents text highlighted for reference purposes.</td>
</tr>
<tr>
    <td><a href="html-menu-tag.php" target="_blank"><code>&lt;menu&gt;</code></a></td>
    <td>Represents a list of commands.</td>
</tr>
<tr>
    <td><a href="html5-menuitem-tag.php" target="_blank"><code>&lt;menuitem&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a list (or menuitem) of commands that a user can perform.</td>
</tr>								
<tr>
    <td><a href="html-meta-tag.php" target="_blank"><code>&lt;meta&gt;</code></a></td>
    <td>Provides structured metadata about the document content.</td>
</tr>
<tr>
    <td><a href="html5-meter-tag.php" target="_blank"><code>&lt;meter&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a scalar measurement within a known range.</td>
</tr>
<tr>
    <td><a href="html5-nav-tag.php" target="_blank"><code>&lt;nav&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a section of navigation links.</td>
</tr>
<tr>
    <td><a href="html-noframes-tag.php" target="_blank" class="obsolete-tag"><code>&lt;noframes&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Defines an alternate content that displays in browsers that do not support frames.</td>
</tr>
<tr>
    <td><a href="html-noscript-tag.php" target="_blank"><code>&lt;noscript&gt;</code></a></td>
    <td>Defines alternative content to display when the browser doesn't support scripting.</td>
</tr>
<tr>
    <td><a href="html-object-tag.php" target="_blank"><code>&lt;object&gt;</code></a></td>
    <td>Defines an embedded object.</td>
</tr>
<tr>
    <td><a href="html-ol-tag.php" target="_blank"><code>&lt;ol&gt;</code></a></td>
    <td>Defines an ordered list.</td>
</tr>
<tr>
    <td><a href="html-optgroup-tag.php" target="_blank"><code>&lt;optgroup&gt;</code></a></td>
    <td>Defines a group of related options in a selection list.</td>
</tr>
<tr>
    <td><a href="html-option-tag.php" target="_blank"><code>&lt;option&gt;</code></a></td>
    <td>Defines an option in a selection list.</td>
</tr>
<tr>
    <td><a href="html5-output-tag.php" target="_blank"><code>&lt;output&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents the result of a calculation.</td>
</tr>
<tr>
    <td><a href="html-p-tag.php" target="_blank"><code>&lt;p&gt;</code></a></td>
    <td>Defines a paragraph.</td>
</tr>
<tr>
    <td><a href="html-param-tag.php" target="_blank"><code>&lt;param&gt;</code></a></td>
    <td>Defines a parameter for an object or applet element.</td>
</tr>
<tr>
    <td><a href="html5-picture-tag.php" target="_blank"><code>&lt;picture&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a container for multiple image sources.</td>
</tr>
<tr>
    <td><a href="html-pre-tag.php" target="_blank"><code>&lt;pre&gt;</code></a></td>
    <td>Defines a block of preformatted text.</td>
</tr>
<tr>
    <td><a href="html5-progress-tag.php" target="_blank"><code>&lt;progress&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents the completion progress of a task.</td>
</tr>
<tr>
    <td><a href="html-q-tag.php" target="_blank"><code>&lt;q&gt;</code></a></td>
    <td>Defines a short inline quotation.</td>
</tr>
<tr>
    <td><a href="html5-rp-tag.php" target="_blank"><code>&lt;rp&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Provides fall-back parenthesis for browsers that that don't support ruby annotations.</td>
</tr>
<tr>
    <td><a href="html5-rt-tag.php" target="_blank"><code>&lt;rt&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines the pronunciation of character presented in a ruby annotations.</td>
</tr>
<tr>
    <td><a href="html5-ruby-tag.php" target="_blank"><code>&lt;ruby&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a ruby annotation.</td>
</tr>
<tr>
    <td><a href="html-s-tag.php" target="_blank"><code>&lt;s&gt;</code></a></td>
    <td>Represents contents that are no longer accurate or no longer relevant.</td>
</tr>
<tr>
    <td><a href="html-samp-tag.php" target="_blank"><code>&lt;samp&gt;</code></a></td>
    <td>Specifies text as sample output from a computer program.</td>
</tr>
<tr>
    <td><a href="html-script-tag.php" target="_blank"><code>&lt;script&gt;</code></a></td>
    <td>Places script in the document for client-side processing.</td>
</tr>
<tr>
    <td><a href="html5-section-tag.php" target="_blank"><code>&lt;section&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a section of a document, such as header, footer etc.</td>
</tr>
<tr>
    <td><a href="html-select-tag.php" target="_blank"><code>&lt;select&gt;</code></a></td>
    <td>Defines a selection list within a form.</td>
</tr>
<tr>
    <td><a href="html-small-tag.php" target="_blank"><code>&lt;small&gt;</code></a></td>
    <td>Displays text in a smaller size.</td>
</tr>
<tr>
    <td><a href="html5-source-tag.php" target="_blank"><code>&lt;source&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines alternative media resources for the media elements like <a href="html5-audio-tag.php" target="_blank"><code>&lt;audio&gt;</code></a> or <a href="html5-video-tag.php" target="_blank"><code>&lt;video&gt;</code></a>.</td>
</tr>
<tr>
    <td><a href="html-span-tag.php" target="_blank"><code>&lt;span&gt;</code></a></td>
    <td>Defines an inline styleless section in a document.</td>
</tr>
<tr>
    <td><a href="html-strike-tag.php" target="_blank" class="obsolete-tag"><code>&lt;strike&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Displays text in strikethrough style.</td>
</tr>
<tr>
    <td><a href="html-strong-tag.php" target="_blank"><code>&lt;strong&gt;</code></a></td>
    <td>Indicate strongly emphasized text.</td>
</tr>
<tr>
    <td><a href="html-style-tag.php" target="_blank"><code>&lt;style&gt;</code></a></td>
    <td>Inserts style information (commonly CSS) into the head of a document.</td>
</tr>
<tr>
    <td><a href="html-sub-tag.php" target="_blank"><code>&lt;sub&gt;</code></a></td>
    <td>Defines subscripted text.</td>
</tr>
<tr>
    <td><a href="html5-summary-tag.php" target="_blank"><code>&lt;summary&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines a summary for the <a href="html5-details-tag.php" target="_blank"><code>&lt;details&gt;</code></a> element.</td>
</tr>
<tr>
    <td><a href="html-sup-tag.php" target="_blank"><code>&lt;sup&gt;</code></a></td>
    <td>Defines superscripted text.</td>
</tr>
<tr>
    <td><a href="html5-svg-tag.php" target="_blank"><code>&lt;svg&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Embed SVG (Scalable Vector Graphics) content in an HTML document.</td>
</tr>
<tr>
    <td><a href="html-table-tag.php" target="_blank"><code>&lt;table&gt;</code></a></td>
    <td>Defines a data table.</td>
</tr>
<tr>
    <td><a href="html-tbody-tag.php" target="_blank"><code>&lt;tbody&gt;</code></a></td>
    <td>Groups a set of rows defining the main body of the table data.</td>
</tr>
<tr>
    <td><a href="html-td-tag.php" target="_blank"><code>&lt;td&gt;</code></a></td>
    <td>Defines a cell in a table.</td>
</tr>
<tr>
    <td><a href="html5-template-tag.php" target="_blank"><code>&lt;template&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines the fragments of HTML that should be hidden when the page is loaded, but can be cloned and inserted in the document by JavaScript.</td>
</tr>
<tr>
    <td><a href="html-textarea-tag.php" target="_blank"><code>&lt;textarea&gt;</code></a></td>
    <td>Defines a multi-line text input control (text area).</td>
</tr>
<tr>
    <td><a href="html-tfoot-tag.php" target="_blank"><code>&lt;tfoot&gt;</code></a></td>
    <td>Groups a set of rows summarizing the columns of the table.</td>
</tr>
<tr>
    <td><a href="html-th-tag.php" target="_blank"><code>&lt;th&gt;</code></a></td>
    <td>Defines a header cell in a table.</td>
</tr>
<tr>
    <td><a href="html-thead-tag.php" target="_blank"><code>&lt;thead&gt;</code></a></td>
    <td>Groups a set of rows that describes the column labels of a table.</td>
</tr>
<tr>
    <td><a href="html5-time-tag.php" target="_blank"><code>&lt;time&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a time and/or date.</td>
</tr>
<tr>
    <td><a href="html-title-tag.php" target="_blank"><code>&lt;title&gt;</code></a></td>
    <td>Defines a title for the document.</td>
</tr>
<tr>
    <td><a href="html-tr-tag.php" target="_blank"><code>&lt;tr&gt;</code></a></td>
    <td>Defines a row of cells in a table.</td>
</tr>
<tr>
    <td><a href="html5-track-tag.php" target="_blank"><code>&lt;track&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Defines text tracks for the media elements like <a href="html5-audio-tag.php" target="_blank"><code>&lt;audio&gt;</code></a> or <a href="html5-video-tag.php" target="_blank"><code>&lt;video&gt;</code></a>.</td>
</tr>
<tr>
    <td><a href="html-tt-tag.php" target="_blank" class="obsolete-tag"><code>&lt;tt&gt;</code></a></td>
    <td><a href="../definitions.php#obsolete" class="obsolete" title="Not supported in HTML5">Obsolete</a> Displays text in a teletype style.</td>
</tr>
<tr>
    <td><a href="html-u-tag.php" target="_blank"><code>&lt;u&gt;</code></a></td>
    <td>Displays text with an underline.</td>
</tr>
<tr>
    <td><a href="html-ul-tag.php" target="_blank"><code>&lt;ul&gt;</code></a></td>
    <td>Defines an unordered list.</td>
</tr>
<tr>
    <td><a href="html-var-tag.php" target="_blank"><code>&lt;var&gt;</code></a></td>
    <td>Defines a variable.</td>
</tr>
<tr>
    <td><a href="html5-video-tag.php" target="_blank"><code>&lt;video&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Embeds video content in an HTML document.</td>
</tr>
<tr>
    <td><a href="html5-wbr-tag.php" target="_blank"><code>&lt;wbr&gt;</code></a> <span class="html5-badge" title="New in HTML5"></span></td>
    <td>Represents a line break opportunity.</td>
</tr>
</tbody>
</table>
</div>


<a  href="https://www.tutorialrepublic.com/html-reference/html5-tags.php">Referenced by from tutorialrepublic.com</a>

---
---

<a href="https://www.javatpoint.com/java-string-format" id=""><h2>Java String Format Specifiers</h2></a>

click above to see more.

---
---


<a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Status#server_error_responses" id="http-response-status-codes"><h2>HTTP response status codes</h2></a>
<div>
<p>
  HTTP response status codes indicate whether a specific <a href="/en-US/docs/Web/HTTP">HTTP</a> request has been successfully completed.
  Responses are grouped in five classes:
</p>
<ol>
  <li><a href="#information_responses">Informational responses</a> (<code>100</code>–<code>199</code>)</li>
  <li><a href="#successful_responses">Successful responses</a> (<code>200</code>–<code>299</code>)</li>
  <li><a href="#redirection_messages">Redirection messages</a> (<code>300</code>–<code>399</code>)</li>
  <li><a href="#client_error_responses">Client error responses</a> (<code>400</code>–<code>499</code>)</li>
  <li><a href="#server_error_responses">Server error responses</a> (<code>500</code>–<code>599</code>)</li>
</ol>
<p>The below status codes are defined by <a href="https://datatracker.ietf.org/doc/html/rfc2616#section-10" class="external" rel=" noopener">section 10 of RFC 2616</a>. You can find an updated specification in <a href="https://datatracker.ietf.org/doc/html/rfc7231#section-6" class="external" rel=" noopener">RFC 7231</a>.</p>
<div class="notecard note" id="sect1">
  <p><strong>Note:</strong> If you receive a response that is not in <a href="#information-responses">this list</a>, it is a non-standard response, possibly custom to the server's software.</p>
</div></div><h2 id="information_responses"><a href="#information_responses" title="Permalink to Information responses">Information responses</a></h2><div><dl>
  <dt id="100_continue"><a href="/en-US/docs/Web/HTTP/Status/100"><code>100 Continue</code></a></dt>
  <dd>
    <p>This interim response indicates that the client should continue the request or ignore the response if the request is already finished.</p>
  </dd>
  <dt id="101_switching_protocols"><a href="/en-US/docs/Web/HTTP/Status/101"><code>101 Switching Protocols</code></a></dt>
  <dd>
    <p>This code is sent in response to an <a href="/en-US/docs/Web/HTTP/Headers/Upgrade"><code>Upgrade</code></a> request header from the client and indicates the protocol the server is switching to.</p>
  </dd>
  <dt id="102_processing"><a href="/en-US/docs/Web/HTTP/Status/102" class="page-not-created" title="This is a link to an unwritten page"><code>102 Processing</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>This code indicates that the server has received and is processing the request, but no response is available yet.</p>
  </dd>
  <dt id="103_early_hints"><a href="/en-US/docs/Web/HTTP/Status/103"><code>103 Early Hints</code></a></dt>
  <dd>
    <p>This status code is primarily intended to be used with the <a href="/en-US/docs/Web/HTTP/Headers/Link"><code>Link</code></a> header, letting the user agent start <a href="/en-US/docs/Web/HTML/Link_types/preload">preloading</a> resources while the server prepares a response.</p>
  </dd>
</dl></div><h2 id="successful_responses"><a href="#successful_responses" title="Permalink to Successful responses">Successful responses</a></h2><div><dl>
  <dt id="200_ok"><a href="/en-US/docs/Web/HTTP/Status/200"><code>200 OK</code></a></dt>
  <dd>
    <p>The request succeeded. The result meaning of "success" depends on the HTTP method:</p>
    <ul>
      <li><code>GET</code>: The resource has been fetched and transmitted in the message body.</li>
      <li><code>HEAD</code>: The representation headers are included in the response without any message body.</li>
      <li><code>PUT</code> or <code>POST</code>: The resource describing the result of the action is transmitted in the message body.</li>
      <li><code>TRACE</code>: The message body contains the request message as received by the server.</li>
    </ul>
  </dd>
  <dt id="201_created"><a href="/en-US/docs/Web/HTTP/Status/201"><code>201 Created</code></a></dt>
  <dd>
    <p>The request succeeded, and a new resource was created as a result. This is typically the response sent after <code>POST</code> requests, or some <code>PUT</code> requests.</p>
  </dd>
  <dt id="202_accepted"><a href="/en-US/docs/Web/HTTP/Status/202"><code>202 Accepted</code></a></dt>
  <dd>
    <p>
      The request has been received but not yet acted upon.
      It is noncommittal, since there is no way in HTTP to later send an asynchronous response indicating the outcome of the request.
      It is intended for cases where another process or server handles the request, or for batch processing.
    </p>
  </dd>
  <dt id="203_non-authoritative_information"><a href="/en-US/docs/Web/HTTP/Status/203"><code>203 Non-Authoritative Information</code></a></dt>
  <dd>
    <p>
      This response code means the returned metadata is not exactly the same as is available from the origin server, but is collected from a local or a third-party copy.
      This is mostly used for mirrors or backups of another resource.
      Except for that specific case, the <code>200 OK</code> response is preferred to this status.
    </p>
  </dd>
  <dt id="204_no_content"><a href="/en-US/docs/Web/HTTP/Status/204"><code>204 No Content</code></a></dt>
  <dd>
    <p>
      There is no content to send for this request, but the headers may be useful.
      The user agent may update its cached headers for this resource with the new ones.
    </p>
  </dd>
  <dt id="205_reset_content"><a href="/en-US/docs/Web/HTTP/Status/205"><code>205 Reset Content</code></a></dt>
  <dd>
    <p>Tells the user agent to reset the document which sent this request.</p>
  </dd>
  <dt id="206_partial_content"><a href="/en-US/docs/Web/HTTP/Status/206"><code>206 Partial Content</code></a></dt>
  <dd>
    <p>This response code is used when the <a href="/en-US/docs/Web/HTTP/Headers/Range"><code>Range</code></a> header is sent from the client to request only part of a resource.</p>
  </dd>
  <dt id="207_multi-status"><a href="/en-US/docs/Web/HTTP/Status/207" class="page-not-created" title="This is a link to an unwritten page"><code>207 Multi-Status</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>Conveys information about multiple resources, for situations where multiple status codes might be appropriate.</p>
  </dd>
  <dt id="208_already_reported"><a href="/en-US/docs/Web/HTTP/Status/208" class="page-not-created" title="This is a link to an unwritten page"><code>208 Already Reported</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>Used inside a <code>&lt;dav:propstat&gt;</code> response element to avoid repeatedly enumerating the internal members of multiple bindings to the same collection.</p>
  </dd>
  <dt id="226_im_used"><a href="/en-US/docs/Web/HTTP/Status/226" class="page-not-created" title="This is a link to an unwritten page"><code>226 IM Used</code></a> (<a href="https://datatracker.ietf.org/doc/html/rfc3229" class="external" rel=" noopener">HTTP Delta encoding</a>)</dt>
  <dd>
    <p>The server has fulfilled a <code>GET</code> request for the resource, and the response is a representation of the result of one or more instance-manipulations applied to the current instance.</p>
  </dd>
</dl></div><h2 id="redirection_messages"><a href="#redirection_messages" title="Permalink to Redirection messages">Redirection messages</a></h2><div><dl>
  <dt id="300_multiple_choice"><a href="/en-US/docs/Web/HTTP/Status/300"><code>300 Multiple Choice</code></a></dt>
  <dd>
    <p>The request has more than one possible response. The user agent or user should choose one of them. (There is no standardized way of choosing one of the responses, but HTML links to the possibilities are recommended so the user can pick.)</p>
  </dd>
  <dt id="301_moved_permanently"><a href="/en-US/docs/Web/HTTP/Status/301"><code>301 Moved Permanently</code></a></dt>
  <dd>
    <p>The URL of the requested resource has been changed permanently. The new URL is given in the response.</p>
  </dd>
  <dt id="302_found"><a href="/en-US/docs/Web/HTTP/Status/302"><code>302 Found</code></a></dt>
  <dd>
    <p>
      This response code means that the URI of requested resource has been changed <em>temporarily</em>.
      Further changes in the URI might be made in the future. Therefore, this same URI should be used by the client in future requests.
    </p>
  </dd>
  <dt id="303_see_other"><a href="/en-US/docs/Web/HTTP/Status/303"><code>303 See Other</code></a></dt>
  <dd>
    <p>The server sent this response to direct the client to get the requested resource at another URI with a GET request.</p>
  </dd>
  <dt id="304_not_modified"><a href="/en-US/docs/Web/HTTP/Status/304"><code>304 Not Modified</code></a></dt>
  <dd>
    <p>
      This is used for caching purposes.
      It tells the client that the response has not been modified, so the client can continue to use the same cached version of the response.
    </p>
  </dd>
  <dt id="305_use_proxy"><code>305 Use Proxy</code> <svg class="icon icon-deprecated" tabindex="0">
    <use xlink:href="/assets/badges.svg#icon-deprecated"></use>
</svg></dt>
  <dd>
    <p>
      Defined in a previous version of the HTTP specification to indicate that a requested response must be accessed by a proxy.
      It has been deprecated due to security concerns regarding in-band configuration of a proxy.
    </p>
  </dd>
  <dt id="306_unused"><code>306 unused</code></dt>
  <dd>
    <p>This response code is no longer used; it is just reserved. It was used in a previous version of the HTTP/1.1 specification.</p>
  </dd>
  <dt id="307_temporary_redirect"><a href="/en-US/docs/Web/HTTP/Status/307"><code>307 Temporary Redirect</code></a></dt>
  <dd>
    <p>
      The server sends this response to direct the client to get the requested resource at another URI with same method that was used in the prior request.
      This has the same semantics as the <code>302 Found</code> HTTP response code, with the exception that the user agent <em>must not</em> change the HTTP method used: if a <code>POST</code> was used in the first request, a <code>POST</code> must be used in the second request.
    </p>
  </dd>
  <dt id="308_permanent_redirect"><a href="/en-US/docs/Web/HTTP/Status/308"><code>308 Permanent Redirect</code></a></dt>
  <dd>
    <p>
      This means that the resource is now permanently located at another URI, specified by the <code>Location:</code> HTTP Response header.
      This has the same semantics as the <code>301 Moved Permanently</code> HTTP response code, with the exception that the user agent <em>must not</em> change the HTTP method used: if a <code>POST</code> was used in the first request, a <code>POST</code> must be used in the second request.
    </p>
  </dd>
</dl></div><h2 id="client_error_responses"><a href="#client_error_responses" title="Permalink to Client error responses">Client error responses</a></h2><div><dl>
  <dt id="400_bad_request"><a href="/en-US/docs/Web/HTTP/Status/400"><code>400 Bad Request</code></a></dt>
  <dd>
    <p>The server cannot or will not process the request due to something that is perceived to be a client error (e.g., malformed request syntax, invalid request message framing, or deceptive request routing).</p>
  </dd>
  <dt id="401_unauthorized"><a href="/en-US/docs/Web/HTTP/Status/401"><code>401 Unauthorized</code></a></dt>
  <dd>
    <p>
      Although the HTTP standard specifies "unauthorized", semantically this response means "unauthenticated".
      That is, the client must authenticate itself to get the requested response.
    </p>
  </dd>
  <dt id="402_payment_required"><a href="/en-US/docs/Web/HTTP/Status/402"><code>402 Payment Required</code></a> <svg class="icon icon-experimental" tabindex="0">
    <use xlink:href="/assets/badges.svg#icon-experimental"></use>
</svg></dt>
  <dd>
    <p>
      This response code is reserved for future use.
      The initial aim for creating this code was using it for digital payment systems, however this status code is used very rarely and no standard convention exists.
    </p>
  </dd>
  <dt id="403_forbidden"><a href="/en-US/docs/Web/HTTP/Status/403"><code>403 Forbidden</code></a></dt>
  <dd>
    <p>
      The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource.
      Unlike <code>401 Unauthorized</code>, the client's identity is known to the server.
    </p>
  </dd>
  <dt id="404_not_found"><a href="/en-US/docs/Web/HTTP/Status/404"><code>404 Not Found</code></a></dt>
  <dd>
    <p>
      The server can not find the requested resource.
      In the browser, this means the URL is not recognized.
      In an API, this can also mean that the endpoint is valid but the resource itself does not exist.
      Servers may also send this response instead of <code>403 Forbidden</code> to hide the existence of a resource from an unauthorized client.
      This response code is probably the most well known due to its frequent occurrence on the web.
    </p>
  </dd>
  <dt id="405_method_not_allowed"><a href="/en-US/docs/Web/HTTP/Status/405"><code>405 Method Not Allowed</code></a></dt>
  <dd>
    <p>
      The request method is known by the server but is not supported by the target resource.
      For example, an API may not allow calling <code>DELETE</code> to remove a resource.
    </p>
  </dd>
  <dt id="406_not_acceptable"><a href="/en-US/docs/Web/HTTP/Status/406"><code>406 Not Acceptable</code></a></dt>
  <dd>
    <p>This response is sent when the web server, after performing <a href="/en-US/docs/Web/HTTP/Content_negotiation#server-driven_negotiation">server-driven content negotiation</a>, doesn't find any content that conforms to the criteria given by the user agent.</p>
  </dd>
  <dt id="407_proxy_authentication_required"><a href="/en-US/docs/Web/HTTP/Status/407"><code>407 Proxy Authentication Required</code></a></dt>
  <dd>
    <p>This is similar to <code>401 Unauthorized</code> but authentication is needed to be done by a proxy.</p>
  </dd>
  <dt id="408_request_timeout"><a href="/en-US/docs/Web/HTTP/Status/408"><code>408 Request Timeout</code></a></dt>
  <dd>
    <p>
      This response is sent on an idle connection by some servers, even without any previous request by the client.
      It means that the server would like to shut down this unused connection.
      This response is used much more since some browsers, like Chrome, Firefox 27+, or IE9, use HTTP pre-connection mechanisms to speed up surfing.
      Also note that some servers merely shut down the connection without sending this message.
    </p>
  </dd>
  <dt id="409_conflict"><a href="/en-US/docs/Web/HTTP/Status/409"><code>409 Conflict</code></a></dt>
  <dd>
    <p>This response is sent when a request conflicts with the current state of the server.</p>
  </dd>
  <dt id="410_gone"><a href="/en-US/docs/Web/HTTP/Status/410"><code>410 Gone</code></a></dt>
  <dd>
    <p>
      This response is sent when the requested content has been permanently deleted from server, with no forwarding address.
      Clients are expected to remove their caches and links to the resource.
      The HTTP specification intends this status code to be used for "limited-time, promotional services".
      APIs should not feel compelled to indicate resources that have been deleted with this status code.
    </p>
  </dd>
  <dt id="411_length_required"><a href="/en-US/docs/Web/HTTP/Status/411"><code>411 Length Required</code></a></dt>
  <dd>
    <p>Server rejected the request because the <code>Content-Length</code> header field is not defined and the server requires it.</p>
  </dd>
  <dt id="412_precondition_failed"><a href="/en-US/docs/Web/HTTP/Status/412"><code>412 Precondition Failed</code></a></dt>
  <dd>
    <p>The client has indicated preconditions in its headers which the server does not meet.</p>
  </dd>
  <dt id="413_payload_too_large"><a href="/en-US/docs/Web/HTTP/Status/413"><code>413 Payload Too Large</code></a></dt>
  <dd>
    <p>
      Request entity is larger than limits defined by server.
      The server might close the connection or return an <code>Retry-After</code> header field.
    </p>
  </dd>
  <dt id="414_uri_too_long"><a href="/en-US/docs/Web/HTTP/Status/414"><code>414 URI Too Long</code></a></dt>
  <dd>
    <p>The URI requested by the client is longer than the server is willing to interpret.</p>
  </dd>
  <dt id="415_unsupported_media_type"><a href="/en-US/docs/Web/HTTP/Status/415"><code>415 Unsupported Media Type</code></a></dt>
  <dd>
    <p>The media format of the requested data is not supported by the server, so the server is rejecting the request.</p>
  </dd>
  <dt id="416_range_not_satisfiable"><a href="/en-US/docs/Web/HTTP/Status/416"><code>416 Range Not Satisfiable</code></a></dt>
  <dd>
    <p>
      The range specified by the <code>Range</code> header field in the request cannot be fulfilled.
      It's possible that the range is outside the size of the target URI's data.
    </p>
  </dd>
  <dt id="417_expectation_failed"><a href="/en-US/docs/Web/HTTP/Status/417"><code>417 Expectation Failed</code></a></dt>
  <dd>
    <p>This response code means the expectation indicated by the <code>Expect</code> request header field cannot be met by the server.</p>
  </dd>
  <dt id="418_im_a_teapot"><a href="/en-US/docs/Web/HTTP/Status/418"><code>418 I'm a teapot</code></a></dt>
  <dd>
    <p>The server refuses the attempt to brew coffee with a teapot.</p>
  </dd>
  <dt id="421_misdirected_request"><a href="/en-US/docs/Web/HTTP/Status/421" class="page-not-created" title="This is a link to an unwritten page"><code>421 Misdirected Request</code></a></dt>
  <dd>
    <p>
      The request was directed at a server that is not able to produce a response.
      This can be sent by a server that is not configured to produce responses for the combination of scheme and authority that are included in the request URI.
    </p>
  </dd>
  <dt id="422_unprocessable_entity"><a href="/en-US/docs/Web/HTTP/Status/422"><code>422 Unprocessable Entity</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>The request was well-formed but was unable to be followed due to semantic errors.</p>
  </dd>
  <dt id="423_locked"><a href="/en-US/docs/Web/HTTP/Status/423" class="page-not-created" title="This is a link to an unwritten page"><code>423 Locked</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>The resource that is being accessed is locked.</p>
  </dd>
  <dt id="424_failed_dependency"><a href="/en-US/docs/Web/HTTP/Status/424" class="page-not-created" title="This is a link to an unwritten page"><code>424 Failed Dependency</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>The request failed due to failure of a previous request.</p>
  </dd>
  <dt id="425_too_early"><a href="/en-US/docs/Web/HTTP/Status/425"><code>425 Too Early</code></a> <svg class="icon icon-experimental" tabindex="0">
    <use xlink:href="/assets/badges.svg#icon-experimental"></use>
</svg></dt>
  <dd>
    <p>Indicates that the server is unwilling to risk processing a request that might be replayed.</p>
  </dd>
  <dt id="426_upgrade_required"><a href="/en-US/docs/Web/HTTP/Status/426"><code>426 Upgrade Required</code></a></dt>
  <dd>
    <p>
      The server refuses to perform the request using the current protocol but might be willing to do so after the client upgrades to a different protocol.
      The server sends an <a href="/en-US/docs/Web/HTTP/Headers/Upgrade"><code>Upgrade</code></a> header in a 426 response to indicate the required protocol(s).
    </p>
  </dd>
  <dt id="428_precondition_required"><a href="/en-US/docs/Web/HTTP/Status/428"><code>428 Precondition Required</code></a></dt>
  <dd>
    <p>
      The origin server requires the request to be conditional.
      This response is intended to prevent the 'lost update' problem, where a client <code>GET</code>s a resource's state, modifies it and <code>PUT</code>s it back to the server, when meanwhile a third party has modified the state on the server, leading to a conflict.
    </p>
  </dd>
  <dt id="429_too_many_requests"><a href="/en-US/docs/Web/HTTP/Status/429"><code>429 Too Many Requests</code></a></dt>
  <dd>
    <p>The user has sent too many requests in a given amount of time ("rate limiting").</p>
  </dd>
  <dt id="431_request_header_fields_too_large"><a href="/en-US/docs/Web/HTTP/Status/431"><code>431 Request Header Fields Too Large</code></a></dt>
  <dd>
    <p>
      The server is unwilling to process the request because its header fields are too large.
      The request may be resubmitted after reducing the size of the request header fields.
    </p>
  </dd>
  <dt id="451_unavailable_for_legal_reasons"><a href="/en-US/docs/Web/HTTP/Status/451"><code>451 Unavailable For Legal Reasons</code></a></dt>
  <dd>
    <p>The user agent requested a resource that cannot legally be provided, such as a web page censored by a government.</p>
  </dd>
</dl></div><h2 id="server_error_responses"><a href="#server_error_responses" title="Permalink to Server error responses">Server error responses</a></h2><div><dl>
  <dt id="500_internal_server_error"><a href="/en-US/docs/Web/HTTP/Status/500"><code>500 Internal Server Error</code></a></dt>
  <dd>
    <p>The server has encountered a situation it does not know how to handle.</p>
  </dd>
  <dt id="501_not_implemented"><a href="/en-US/docs/Web/HTTP/Status/501"><code>501 Not Implemented</code></a></dt>
  <dd>
    <p>The request method is not supported by the server and cannot be handled. The only methods that servers are required to support (and therefore that must not return this code) are <code>GET</code> and <code>HEAD</code>.</p>
  </dd>
  <dt id="502_bad_gateway"><a href="/en-US/docs/Web/HTTP/Status/502"><code>502 Bad Gateway</code></a></dt>
  <dd>
    <p>This error response means that the server, while working as a gateway to get a response needed to handle the request, got an invalid response.</p>
  </dd>
  <dt id="503_service_unavailable"><a href="/en-US/docs/Web/HTTP/Status/503"><code>503 Service Unavailable</code></a></dt>
  <dd>
    <p>
      The server is not ready to handle the request.
      Common causes are a server that is down for maintenance or that is overloaded.
      Note that together with this response, a user-friendly page explaining the problem should be sent.
      This response should be used for temporary conditions and the <code>Retry-After</code> HTTP header should, if possible, contain the estimated time before the recovery of the service.
      The webmaster must also take care about the caching-related headers that are sent along with this response, as these temporary condition responses should usually not be cached.
    </p>
  </dd>
  <dt id="504_gateway_timeout"><a href="/en-US/docs/Web/HTTP/Status/504"><code>504 Gateway Timeout</code></a></dt>
  <dd>
    <p>This error response is given when the server is acting as a gateway and cannot get a response in time.</p>
  </dd>
  <dt id="505_http_version_not_supported"><a href="/en-US/docs/Web/HTTP/Status/505"><code>505 HTTP Version Not Supported</code></a></dt>
  <dd>
    <p>The HTTP version used in the request is not supported by the server.</p>
  </dd>
  <dt id="506_variant_also_negotiates"><a href="/en-US/docs/Web/HTTP/Status/506"><code>506 Variant Also Negotiates</code></a></dt>
  <dd>
    <p>The server has an internal configuration error: the chosen variant resource is configured to engage in transparent content negotiation itself, and is therefore not a proper end point in the negotiation process.</p>
  </dd>
  <dt id="507_insufficient_storage"><a href="/en-US/docs/Web/HTTP/Status/507"><code>507 Insufficient Storage</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>The method could not be performed on the resource because the server is unable to store the representation needed to successfully complete the request.</p>
  </dd>
  <dt id="508_loop_detected"><a href="/en-US/docs/Web/HTTP/Status/508"><code>508 Loop Detected</code></a> (<a href="/en-US/docs/Glossary/WebDAV">WebDAV</a>)</dt>
  <dd>
    <p>The server detected an infinite loop while processing the request.</p>
  </dd>
  <dt id="510_not_extended"><a href="/en-US/docs/Web/HTTP/Status/510"><code>510 Not Extended</code></a></dt>
  <dd>
    <p>Further extensions to the request are required for the server to fulfill it.</p>
  </dd>
  <dt id="511_network_authentication_required"><a href="/en-US/docs/Web/HTTP/Status/511"><code>511 Network Authentication Required</code></a></dt>
  <dd>
    <p>Indicates that the client needs to authenticate to gain network access.</p>
  </dd>
</dl></div><h2 id="browser_compatibility"><a href="#browser_compatibility" title="Permalink to Browser compatibility">Browser compatibility</a></h2><a class="bc-github-link external external-icon" href="https://github.com/mdn/browser-compat-data/issues/new?body=%3C%21--+Tips%3A+where+applicable%2C+specify+browser+name%2C+browser+version%2C+and+mobile+operating+system+version+--%3E%0A%0A%23%23%23%23+What+information+was+incorrect%2C+unhelpful%2C+or+incomplete%3F%0A%0A%23%23%23%23+What+did+you+expect+to+see%3F%0A%0A%23%23%23%23+Did+you+test+this%3F+If+so%2C+how%3F%0A%0A%0A%3C%21--+Do+not+make+changes+below+this+line+--%3E%0A%3Cdetails%3E%0A%3Csummary%3EMDN+page+report+details%3C%2Fsummary%3E%0A%0A*+Query%3A+%60http.status%60%0A*+MDN+URL%3A+https%3A%2F%2Fdeveloper.mozilla.org%2Fen-US%2Fdocs%2FWeb%2FHTTP%2FStatus%0A*+Report+started%3A+2022-02-27T02%3A03%3A57.922Z%0A%0A%3C%2Fdetails%3E&amp;title=http.status+-+%3CPUT+TITLE+HERE%3E" target="_blank" rel="noopener noreferrer" title="Report an issue with this compatibility data">Report problems with this compatibility data on GitHub</a><table class="bc-table bc-table-web"><thead><tr class="bc-platforms"><td></td><th class="bc-platform-desktop" colspan="6" title="desktop"><span>desktop</span></th><th class="bc-platform-mobile" colspan="6" title="mobile"><span>mobile</span></th></tr><tr class="bc-browsers"><td></td><th class="bc-browser-chrome"><span class="bc-head-txt-label bc-head-icon-chrome">Chrome</span></th><th class="bc-browser-edge"><span class="bc-head-txt-label bc-head-icon-edge">Edge</span></th><th class="bc-browser-firefox"><span class="bc-head-txt-label bc-head-icon-firefox">Firefox</span></th><th class="bc-browser-ie"><span class="bc-head-txt-label bc-head-icon-ie">Internet Explorer</span></th><th class="bc-browser-opera"><span class="bc-head-txt-label bc-head-icon-opera">Opera</span></th><th class="bc-browser-safari"><span class="bc-head-txt-label bc-head-icon-safari">Safari</span></th><th class="bc-browser-webview_android"><span class="bc-head-txt-label bc-head-icon-webview_android">WebView Android</span></th><th class="bc-browser-chrome_android"><span class="bc-head-txt-label bc-head-icon-chrome_android">Chrome Android</span></th><th class="bc-browser-firefox_android"><span class="bc-head-txt-label bc-head-icon-firefox_android">Firefox for Android</span></th><th class="bc-browser-opera_android"><span class="bc-head-txt-label bc-head-icon-opera_android">Opera Android</span></th><th class="bc-browser-safari_ios"><span class="bc-head-txt-label bc-head-icon-safari_ios">Safari on iOS</span></th><th class="bc-browser-samsunginternet_android"><span class="bc-head-txt-label bc-head-icon-samsunginternet_android">Samsung Internet</span></th></tr></thead><tbody><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/100" class="bc-table-row-header"><code>100</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/200" class="bc-table-row-header"><code>200</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/201" class="bc-table-row-header"><code>201</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/204" class="bc-table-row-header"><code>204</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/206" class="bc-table-row-header"><code>206</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/301" class="bc-table-row-header"><code>301</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-exp<table summary="" width="100%" cellspacing="0" cellpadding="3" border="1">
<tbody><tr class="TableHeadingColor" bgcolor="#CCCCFF">
<th colspan="2" align="left"><font size="+2">
<b>Class Summary</b></font></th>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Cache71Dialect.html" title="class in org.hibernate.dialect">Cache71Dialect</a></b></td>
<td>Caché 2007.1 dialect.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DataDirectOracle9Dialect.html" title="class in org.hibernate.dialect">DataDirectOracle9Dialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DB2390Dialect.html" title="class in org.hibernate.dialect">DB2390Dialect</a></b></td>
<td>An SQL dialect for DB2/390.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DB2400Dialect.html" title="class in org.hibernate.dialect">DB2400Dialect</a></b></td>
<td>An SQL dialect for DB2/400.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DB2Dialect.html" title="class in org.hibernate.dialect">DB2Dialect</a></b></td>
<td>An SQL dialect for DB2.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DerbyDialect.html" title="class in org.hibernate.dialect">DerbyDialect</a></b></td>
<td>Hibernate Dialect for Cloudscape 10 - aka Derby.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Dialect.html" title="class in org.hibernate.dialect">Dialect</a></b></td>
<td>Represents a dialect of SQL implemented by a particular RDBMS.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/FirebirdDialect.html" title="class in org.hibernate.dialect">FirebirdDialect</a></b></td>
<td>An SQL dialect for Firebird.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/FrontBaseDialect.html" title="class in org.hibernate.dialect">FrontBaseDialect</a></b></td>
<td>An SQL Dialect for Frontbase.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/H2Dialect.html" title="class in org.hibernate.dialect">H2Dialect</a></b></td>
<td>A dialect compatible with the H2 database.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/HSQLDialect.html" title="class in org.hibernate.dialect">HSQLDialect</a></b></td>
<td>An SQL dialect compatible with HSQLDB (HyperSQL).</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/HSQLDialect.ReadUncommittedLockingStrategy.html" title="class in org.hibernate.dialect">HSQLDialect.ReadUncommittedLockingStrategy</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/InformixDialect.html" title="class in org.hibernate.dialect">InformixDialect</a></b></td>
<td>Informix dialect.<br>
 <br>
 Seems to work with Informix Dynamic Server Version 7.31.UD3,
 Informix JDBC driver version 2.21JC3.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Ingres10Dialect.html" title="class in org.hibernate.dialect">Ingres10Dialect</a></b></td>
<td>A SQL dialect for Ingres 10 and later versions.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Ingres9Dialect.html" title="class in org.hibernate.dialect">Ingres9Dialect</a></b></td>
<td>A SQL dialect for Ingres 9.3 and later versions.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/IngresDialect.html" title="class in org.hibernate.dialect">IngresDialect</a></b></td>
<td>An SQL dialect for Ingres 9.2.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/InterbaseDialect.html" title="class in org.hibernate.dialect">InterbaseDialect</a></b></td>
<td>An SQL dialect for Interbase.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/JDataStoreDialect.html" title="class in org.hibernate.dialect">JDataStoreDialect</a></b></td>
<td>A <tt>Dialect</tt> for JDataStore.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MckoiDialect.html" title="class in org.hibernate.dialect">MckoiDialect</a></b></td>
<td>An SQL dialect compatible with McKoi SQL</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MimerSQLDialect.html" title="class in org.hibernate.dialect">MimerSQLDialect</a></b></td>
<td>An Hibernate 3 SQL dialect for Mimer SQL.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQL5Dialect.html" title="class in org.hibernate.dialect">MySQL5Dialect</a></b></td>
<td>An SQL dialect for MySQL 5.x specific features.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQL5InnoDBDialect.html" title="class in org.hibernate.dialect">MySQL5InnoDBDialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQLDialect.html" title="class in org.hibernate.dialect">MySQLDialect</a></b></td>
<td>An SQL dialect for MySQL (prior to 5.x).</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQLInnoDBDialect.html" title="class in org.hibernate.dialect">MySQLInnoDBDialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQLMyISAMDialect.html" title="class in org.hibernate.dialect">MySQLMyISAMDialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle10gDialect.html" title="class in org.hibernate.dialect">Oracle10gDialect</a></b></td>
<td>A dialect specifically for use with Oracle 10g.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle8iDialect.html" title="class in org.hibernate.dialect">Oracle8iDialect</a></b></td>
<td>A dialect for Oracle 8i.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle9Dialect.html" title="class in org.hibernate.dialect">Oracle9Dialect</a></b></td>
<td><b>Deprecated.</b>&nbsp;<i>Use either Oracle9iDialect or Oracle10gDialect instead</i></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle9iDialect.html" title="class in org.hibernate.dialect">Oracle9iDialect</a></b></td>
<td>A dialect for Oracle 9i databases.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/OracleDialect.html" title="class in org.hibernate.dialect">OracleDialect</a></b></td>
<td><b>Deprecated.</b>&nbsp;<i>Use Oracle8iDialect instead.</i></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/PointbaseDialect.html" title="class in org.hibernate.dialect">PointbaseDialect</a></b></td>
<td>A <tt>Dialect</tt> for Pointbase.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/PostgresPlusDialect.html" title="class in org.hibernate.dialect">PostgresPlusDialect</a></b></td>
<td>An SQL dialect for Postgres Plus</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/PostgreSQLDialect.html" title="class in org.hibernate.dialect">PostgreSQLDialect</a></b></td>
<td>An SQL dialect for Postgres
 <p>
 For discussion of BLOB "support" in postrges, as of 8.4, have a peek at
 <a href="http://jdbc.postgresql.org/documentation/84/binary-data.html">http://jdbc.postgresql.org/documentation/84/binary-data.html</a>.</p></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/ProgressDialect.html" title="class in org.hibernate.dialect">ProgressDialect</a></b></td>
<td>An SQL dialect compatible with Progress 9.1C<br>
<br>
 Connection Parameters required:

 hibernate.dialect org.hibernate.sql.ProgressDialect
 hibernate.driver com.progress.sql.jdbc.JdbcProgressDriver
 hibernate.url jdbc:JdbcProgress:T:host:port:dbname;WorkArounds=536870912
 hibernate.username username
 hibernate.password password

 The WorkArounds parameter in the URL is required to avoid an error
 in the Progress 9.1C JDBC driver related to PreparedStatements.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/RDMSOS2200Dialect.html" title="class in org.hibernate.dialect">RDMSOS2200Dialect</a></b></td>
<td>This is the Hibernate dialect for the Unisys 2200 Relational Database (RDMS).</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/ResultColumnReferenceStrategy.html" title="class in org.hibernate.dialect">ResultColumnReferenceStrategy</a></b></td>
<td>Defines how we need to reference columns in the group-by, having, and order-by
 clauses.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SAPDBDialect.html" title="class in org.hibernate.dialect">SAPDBDialect</a></b></td>
<td>An SQL dialect compatible with SAP DB.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SQLServer2008Dialect.html" title="class in org.hibernate.dialect">SQLServer2008Dialect</a></b></td>
<td>A dialect for Microsoft SQL Server 2008 with JDBC Driver 3.0 and above</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SQLServerDialect.html" title="class in org.hibernate.dialect">SQLServerDialect</a></b></td>
<td>A dialect for Microsoft SQL Server 2000 and 2005</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Sybase11Dialect.html" title="class in org.hibernate.dialect">Sybase11Dialect</a></b></td>
<td>A SQL dialect suitable for use with Sybase 11.9.2 (specifically: avoids ANSI JOIN syntax)</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SybaseAnywhereDialect.html" title="class in org.hibernate.dialect">SybaseAnywhereDialect</a></b></td>
<td>SQL Dialect for Sybase Anywhere
 extending Sybase (Enterprise) Dialect
 (Tested on ASA 8.x)</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SybaseASE15Dialect.html" title="class in org.hibernate.dialect">SybaseASE15Dialect</a></b></td>
<td>An SQL dialect targetting Sybase Adaptive Server Enterprise (ASE) 15 and higher.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SybaseDialect.html" title="class in org.hibernate.dialect">SybaseDialect</a></b></td>
<td><b>Deprecated.</b>&nbsp;<i>use <code>AbstractTransactSQLDialect</code>,
 <a href="../../../org/hibernate/dialect/SybaseASE15Dialect.html" title="class in org.hibernate.dialect"><code>SybaseASE15Dialect</code></a> or <a href="../../../org/hibernate/dialect/SQLServerDialect.html" title="class in org.hibernate.dialect"><code>SQLServerDialect</code></a> instead depending on
 need.</i></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/TeradataDialect.html" title="class in org.hibernate.dialect">TeradataDialect</a></b></td>
<td>A dialect for the Teradata database created by MCR as part of the
 dialect certification process.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/TimesTenDialect.html" title="class in org.hibernate.dialect">TimesTenDialect</a></b></td>
<td>A SQL dialect for TimesTen 5.1.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/TypeNames.html" title="class in org.hibernate.dialect">TypeNames</a></b></td>
<td>This class maps a type to names.</td>
</tr>
</tbody>
</table>
<span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/302" class="bc-table-row-header"><code>302</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/303" class="bc-table-row-header"><code>303</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/304" class="bc-table-row-header"><code>304</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/307" class="bc-table-row-header"><code>307</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/308" class="bc-table-row-header"><code>308</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false" title="Released 2014-07-16"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>36</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false" title="Released 2012-07-17"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>14</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes bc-has-history" aria-expanded="false" tabindex="0" title="Released 2013-10-17"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>11</span><div class="bc-icons"><abbr class="only-icon" title="footnote"><span>footnote</span><i class="ic-footnote"></i></abbr></div><button type="button" title="Open implementation notes" class="bc-history-link only-icon "><span>Open</span><i class="ic-history" aria-hidden="true"></i></button></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false" title="Released 2014-09-02"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>24</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false" title="Released 2013-10-22"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>7</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false" title="Released 2014-09-03"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>37</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false" title="Released 2014-07-16"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>36</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false" title="Released 2012-06-26"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>14</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false" title="Released 2014-09-10"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>24</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false" title="Released 2013-09-18"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>7</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false" title="Released 2015-04-10"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>3.0</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/401" class="bc-table-row-header"><code>401</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/403" class="bc-table-row-header"><code>403</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/404" class="bc-table-row-header"><code>404</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/406" class="bc-table-row-header"><code>406</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/407" class="bc-table-row-header"><code>407</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/409" class="bc-table-row-header"><code>409</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/410" class="bc-table-row-header"><code>410</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/412" class="bc-table-row-header"><code>412</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/416" class="bc-table-row-header"><code>416</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/418" class="bc-table-row-header"><code>418</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/425" class="bc-table-row-header"><code>425</code></a></th><td class="bc-browser-chrome bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Edge</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false" title="Released 2018-01-23"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>58</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false" title="Released 2018-01-22"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>58</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-unknown " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-unknown only-icon" title="Compatibility unknown; please update this."><span>Compatibility unknown; please update this.</span></abbr><span>?</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/451" class="bc-table-row-header"><code>451</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/500" class="bc-table-row-header"><code>500</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/501" class="bc-table-row-header"><code>501</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/502" class="bc-table-row-header"><code>502</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/503" class="bc-table-row-header"><code>503</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr><tr><th scope="row"><a href="/en-US/docs/Web/HTTP/Status/504" class="bc-table-row-header"><code>504</code></a></th><td class="bc-browser-chrome bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-edge bc-supports-yes " aria-expanded="false" title="Released 2015-07-28"><span class="bc-browser-name">Edge</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>12</span><div class="bc-icons"></div></td><td class="bc-browser-firefox bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-ie bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Internet Explorer</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-webview_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">WebView Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-chrome_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Chrome Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-firefox_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Firefox for Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-opera_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Opera Android</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-safari_ios bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Safari on iOS</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td><td class="bc-browser-samsunginternet_android bc-supports-yes " aria-expanded="false"><span class="bc-browser-name">Samsung Internet</span><abbr class="bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr><span>Yes</span><div class="bc-icons"></div></td></tr></tbody></table><section class="bc-legend"><h3 class="visually-hidden" id="Legend">Legend</h3><dl class="bc-legend-items-container"><div class="bc-legend-item"><dt><span class="bc-supports-yes bc-supports"><abbr class="bc-level bc-level-yes only-icon" title="Full support"><span>Full support</span></abbr></span></dt><dd>Full support</dd></div><div class="bc-legend-item"><dt><span class="bc-supports-unknown bc-supports"><abbr class="bc-level bc-level-unknown only-icon" title="Compatibility unknown"><span>Compatibility unknown</span></abbr></span></dt><dd>Compatibility unknown</dd></div><div class="bc-legend-item"><dt><abbr class="only-icon legend-icons ic-footnote" title="See implementation notes."></abbr></dt><dd>See implementation notes.</dd></div></dl></section><div class="hidden">The compatibility table on this page is generated from structured data. If you'd like to contribute to the data, please check out <a href="https://github.com/mdn/browser-compat-data">https://github.com/mdn/browser-compat-data</a> and send us a pull request.</div><h2 id="see_also"><a href="#see_also" title="Permalink to See also">See also</a></h2><div><ul>
  <li><a href="https://en.wikipedia.org/wiki/List_of_HTTP_status_codes" class="external" rel=" noopener">List of HTTP status codes on Wikipedia</a></li>
  <li><a href="https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml" class="external" rel=" noopener">IANA official registry of HTTP status codes</a></li>
</ul></div>

---
---

<div>

## [Package org.hibernate.dialect ](https://docs.jboss.org/hibernate/orm/3.5/javadocs/org/hibernate/dialect/package-summary.html)

This package abstracts the SQL dialect of the underlying database. 

<div>
<table>
</tr>
<thead>
  <tr>
    <th>Dialect</th>
    <th>Description</th>
  </tr>
</thead>
<tbody><tr class="TableHeadingColor" bgcolor="#ffffff">

<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Cache71Dialect.html" title="class in org.hibernate.dialect">Cache71Dialect</a></b></td>
<td>Caché 2007.1 dialect.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DataDirectOracle9Dialect.html" title="class in org.hibernate.dialect">DataDirectOracle9Dialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DB2390Dialect.html" title="class in org.hibernate.dialect">DB2390Dialect</a></b></td>
<td>An SQL dialect for DB2/390.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DB2400Dialect.html" title="class in org.hibernate.dialect">DB2400Dialect</a></b></td>
<td>An SQL dialect for DB2/400.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DB2Dialect.html" title="class in org.hibernate.dialect">DB2Dialect</a></b></td>
<td>An SQL dialect for DB2.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/DerbyDialect.html" title="class in org.hibernate.dialect">DerbyDialect</a></b></td>
<td>Hibernate Dialect for Cloudscape 10 - aka Derby.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Dialect.html" title="class in org.hibernate.dialect">Dialect</a></b></td>
<td>Represents a dialect of SQL implemented by a particular RDBMS.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/FirebirdDialect.html" title="class in org.hibernate.dialect">FirebirdDialect</a></b></td>
<td>An SQL dialect for Firebird.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/FrontBaseDialect.html" title="class in org.hibernate.dialect">FrontBaseDialect</a></b></td>
<td>An SQL Dialect for Frontbase.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/H2Dialect.html" title="class in org.hibernate.dialect">H2Dialect</a></b></td>
<td>A dialect compatible with the H2 database.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/HSQLDialect.html" title="class in org.hibernate.dialect">HSQLDialect</a></b></td>
<td>An SQL dialect compatible with HSQLDB (HyperSQL).</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/HSQLDialect.ReadUncommittedLockingStrategy.html" title="class in org.hibernate.dialect">HSQLDialect.ReadUncommittedLockingStrategy</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/InformixDialect.html" title="class in org.hibernate.dialect">InformixDialect</a></b></td>
<td>Informix dialect.<br>
 <br>
 Seems to work with Informix Dynamic Server Version 7.31.UD3,
 Informix JDBC driver version 2.21JC3.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Ingres10Dialect.html" title="class in org.hibernate.dialect">Ingres10Dialect</a></b></td>
<td>A SQL dialect for Ingres 10 and later versions.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Ingres9Dialect.html" title="class in org.hibernate.dialect">Ingres9Dialect</a></b></td>
<td>A SQL dialect for Ingres 9.3 and later versions.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/IngresDialect.html" title="class in org.hibernate.dialect">IngresDialect</a></b></td>
<td>An SQL dialect for Ingres 9.2.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/InterbaseDialect.html" title="class in org.hibernate.dialect">InterbaseDialect</a></b></td>
<td>An SQL dialect for Interbase.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/JDataStoreDialect.html" title="class in org.hibernate.dialect">JDataStoreDialect</a></b></td>
<td>A <tt>Dialect</tt> for JDataStore.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MckoiDialect.html" title="class in org.hibernate.dialect">MckoiDialect</a></b></td>
<td>An SQL dialect compatible with McKoi SQL</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MimerSQLDialect.html" title="class in org.hibernate.dialect">MimerSQLDialect</a></b></td>
<td>An Hibernate 3 SQL dialect for Mimer SQL.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQL5Dialect.html" title="class in org.hibernate.dialect">MySQL5Dialect</a></b></td>
<td>An SQL dialect for MySQL 5.x specific features.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQL5InnoDBDialect.html" title="class in org.hibernate.dialect">MySQL5InnoDBDialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQLDialect.html" title="class in org.hibernate.dialect">MySQLDialect</a></b></td>
<td>An SQL dialect for MySQL (prior to 5.x).</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQLInnoDBDialect.html" title="class in org.hibernate.dialect">MySQLInnoDBDialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/MySQLMyISAMDialect.html" title="class in org.hibernate.dialect">MySQLMyISAMDialect</a></b></td>
<td>&nbsp;</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle10gDialect.html" title="class in org.hibernate.dialect">Oracle10gDialect</a></b></td>
<td>A dialect specifically for use with Oracle 10g.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle8iDialect.html" title="class in org.hibernate.dialect">Oracle8iDialect</a></b></td>
<td>A dialect for Oracle 8i.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle9Dialect.html" title="class in org.hibernate.dialect">Oracle9Dialect</a></b></td>
<td><b>Deprecated.</b>&nbsp;<i>Use either Oracle9iDialect or Oracle10gDialect instead</i></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Oracle9iDialect.html" title="class in org.hibernate.dialect">Oracle9iDialect</a></b></td>
<td>A dialect for Oracle 9i databases.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/OracleDialect.html" title="class in org.hibernate.dialect">OracleDialect</a></b></td>
<td><b>Deprecated.</b>&nbsp;<i>Use Oracle8iDialect instead.</i></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/PointbaseDialect.html" title="class in org.hibernate.dialect">PointbaseDialect</a></b></td>
<td>A <tt>Dialect</tt> for Pointbase.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/PostgresPlusDialect.html" title="class in org.hibernate.dialect">PostgresPlusDialect</a></b></td>
<td>An SQL dialect for Postgres Plus</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/PostgreSQLDialect.html" title="class in org.hibernate.dialect">PostgreSQLDialect</a></b></td>
<td>An SQL dialect for Postgres
 <p>
 For discussion of BLOB "support" in postrges, as of 8.4, have a peek at
 <a href="http://jdbc.postgresql.org/documentation/84/binary-data.html">http://jdbc.postgresql.org/documentation/84/binary-data.html</a>.</p></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/ProgressDialect.html" title="class in org.hibernate.dialect">ProgressDialect</a></b></td>
<td>An SQL dialect compatible with Progress 9.1C<br>
<br>
 Connection Parameters required:

 hibernate.dialect org.hibernate.sql.ProgressDialect
 hibernate.driver com.progress.sql.jdbc.JdbcProgressDriver
 hibernate.url jdbc:JdbcProgress:T:host:port:dbname;WorkArounds=536870912
 hibernate.username username
 hibernate.password password

 The WorkArounds parameter in the URL is required to avoid an error
 in the Progress 9.1C JDBC driver related to PreparedStatements.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/RDMSOS2200Dialect.html" title="class in org.hibernate.dialect">RDMSOS2200Dialect</a></b></td>
<td>This is the Hibernate dialect for the Unisys 2200 Relational Database (RDMS).</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/ResultColumnReferenceStrategy.html" title="class in org.hibernate.dialect">ResultColumnReferenceStrategy</a></b></td>
<td>Defines how we need to reference columns in the group-by, having, and order-by
 clauses.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SAPDBDialect.html" title="class in org.hibernate.dialect">SAPDBDialect</a></b></td>
<td>An SQL dialect compatible with SAP DB.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SQLServer2008Dialect.html" title="class in org.hibernate.dialect">SQLServer2008Dialect</a></b></td>
<td>A dialect for Microsoft SQL Server 2008 with JDBC Driver 3.0 and above</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SQLServerDialect.html" title="class in org.hibernate.dialect">SQLServerDialect</a></b></td>
<td>A dialect for Microsoft SQL Server 2000 and 2005</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/Sybase11Dialect.html" title="class in org.hibernate.dialect">Sybase11Dialect</a></b></td>
<td>A SQL dialect suitable for use with Sybase 11.9.2 (specifically: avoids ANSI JOIN syntax)</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SybaseAnywhereDialect.html" title="class in org.hibernate.dialect">SybaseAnywhereDialect</a></b></td>
<td>SQL Dialect for Sybase Anywhere
 extending Sybase (Enterprise) Dialect
 (Tested on ASA 8.x)</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SybaseASE15Dialect.html" title="class in org.hibernate.dialect">SybaseASE15Dialect</a></b></td>
<td>An SQL dialect targetting Sybase Adaptive Server Enterprise (ASE) 15 and higher.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/SybaseDialect.html" title="class in org.hibernate.dialect">SybaseDialect</a></b></td>
<td><b>Deprecated.</b>&nbsp;<i>use <code>AbstractTransactSQLDialect</code>,
 <a href="../../../org/hibernate/dialect/SybaseASE15Dialect.html" title="class in org.hibernate.dialect"><code>SybaseASE15Dialect</code></a> or <a href="../../../org/hibernate/dialect/SQLServerDialect.html" title="class in org.hibernate.dialect"><code>SQLServerDialect</code></a> instead depending on
 need.</i></td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/TeradataDialect.html" title="class in org.hibernate.dialect">TeradataDialect</a></b></td>
<td>A dialect for the Teradata database created by MCR as part of the
 dialect certification process.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/TimesTenDialect.html" title="class in org.hibernate.dialect">TimesTenDialect</a></b></td>
<td>A SQL dialect for TimesTen 5.1.</td>
</tr>
<tr class="TableRowColor" bgcolor="black">
<td width="15%"><b><a href="../../../org/hibernate/dialect/TypeNames.html" title="class in org.hibernate.dialect">TypeNames</a></b></td>
<td>This class maps a type to names.</td>
</tr>
</tbody>
</table>
</div>
</div>

---
---