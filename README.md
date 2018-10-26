# PDFLoader
PDFLoader is a webapplication which has the capability to extract all the PDF files in your Google Drive.

Before building this project you have to create your credentials. 
```
https://console.cloud.google.com/apis/credentials
```

Go to the above link and create a project in the console. Then go to the OAuth consent screen and create add the scopes you need to let this application access. For this appliation you need to allow ../auth/drive scope as well. Then you can save the changes you have done and go back to the Credentials tab and create new credentials, both API key and OAuth Client ID for a web application.

To find out clear steps to create the credentials follow 
```
https://developers.google.com/drive/api/v3/quickstart/js.
```
After creating the credentials you have to download the file and rename it as client_secret.json and bring that to the src/main/resources folder.

Now you are ready to build the project and give it a go.

To build the project with maven use below command.
```
mvn clean install
```
or
```
mvn package
```
Then depoly the .war file which is in "target" folder, in tomcat.

Then run the applcation in the browser.
