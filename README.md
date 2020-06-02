# Smash.GG Mobile Dashboard

This project is dedicated to creating a Mobile App for the site smash.gg for bracket management. Currently only in development for Android devices.

## Documentation

* [Smash.gg API](https://smashgg-developer-portal.netlify.app/docs/intro)
* [Smash.gg Schema](https://smashgg-schema.netlify.app/reference/query.doc.html)
* [Android Studio](https://developer.android.com/docs)
* [GraphQL](https://graphql.org/learn/)

## API Key
For privacy, developers need to request their own API key from smash.gg. For instructions on that [**Click here**](https://smashgg-developer-portal.netlify.app/docs/authentication)

Once you have your API key, create a new Java class in \app\src\main\java\com\example\smashggmobiledashboard named Constants.java. This class is currently in the .gitignore file so you can safely reference your APIKey through here if needed. 

```
public class Constants {
    public static String APIKey = "XXXXXXXXXXXXXXXXXX";
}
```

To reference it:
```
String ex = Constants.APIKey;
```
