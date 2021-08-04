# About

This authenticated extension app allows to test the extensibility API by exporting
controller services to the client's scope allowing to execute commands
and checking their expectations.

## How does it work?

For each functional domain we expose a driver service  
Let's take for example Application navigation, what allows to apps to register apps into the apps menu  
Once the app installed in the client, and from browser console:

```javascript
const navDriver = SYMPHONY.services.subscribe('e2eextapp:navigation');
```

Will provide you access to the navigation driver, you can execute then commands in the following format
to drive the app's behaviour:

```javascript
navDriver.execute({
  action: 'ADD',
  payload: { id: '1', title: 'App Navigation' },
});
```

Will add a navigation menu. Check the navigation driver file to see all the possible commands

## Quick start

```shell script
mvn install
mvn spring-boot:run
```

## Configuration

The app is preconfigured to run in devx1.  
To configure it in a new POD, use the appId : e2e-ext-app  
And use the public key in the RSA directory at project's root

## Running Locally

```shell script
./SFE-Lite/extension-apps/e2e-ext-app/startup.sh --podUrl <podUrl> --podPort <podPort>
```

- `podUrl` => Uses the podUrl. Example: devx3.symphony.com
- `podPort` => Uses the podPort. Example: 443

The script will change the config.json with the parameters and run the quick start.

## Add pod certificates to truststore

Sometimes in order to authenticate the app on a self-signed POD certificate
Certificate should be added to Java's truststore.

```shell script
openssl s_client -connect devx3.symphony.com:443 -showcerts > devx3.cert
sudo $JAVA_HOME/bin/keytool -import -noprompt -trustcacerts -alias devx1 -file server.cert -keystore $JAVA_HOME/lib/security/cacerts
```
