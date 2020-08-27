This is not the library but let's say a few methods to do the job for you. 

Here's the article<linkhere> to show how things are working and if you don't want to add library. You can get the simple method to do job for you.

Textster help you to create stylized texts which are localizable and dynamic.

Download:
--------

```
allprojects {
   repositories {
      maven { url 'https://jitpack.io' }
   }
}
```


```
dependencies {
    implementation 'com.github.tolgacanunal:textster:0.1.0'
}
```

Usage:
--------

### bold:

```
<string name="thanks_message"><annotation type="bold">Thanks</annotation><annotation type="bold"> for reading through the documentation.</string>
```

```
val thanksAnnotatedString = AnnotatedString(R.string.thanks_message)
thanksMessageTextView.text = thanksAnnotatedString.getStyledCharSequence(thanksMessageTextView.context)
```

### font:

```
<string name="thanks_message"><annotation font="publicsans_medium">Thanks</annotation> for reading through the documentation.</string>
```

```
val thanksAnnotatedString = AnnotatedString(R.string.thanks_message)
thanksMessageTextView.text = thanksAnnotatedString.getStyledCharSequence(thanksMessageTextView.context)
```

### color:

```
<string name="thanks_message"><annotation color="#FF5733">Thanks</annotation> for reading through the documentation.</string>
```

```
val thanksAnnotatedString = AnnotatedString(R.string.thanks_message)
thanksMessageTextView.text = thanksAnnotatedString.getStyledCharSequence(thanksMessageTextView.context)
```

If you don't want to give hardcoded RGB color, you can use the example below:

```
<string name="thanks_message"><annotation custom="thanks_color">Thanks</annotation> for reading through the documentation.</string>
```
  
```
val thanksBlockColor = ContextCompat.getColor(context, R.color.blue_400)
val thanksXmlStyleOption = XmlStyleOption(customAnnotations = listOf("thanks_color" to ForegroundColorSpan(thanksBlockColor)
val thanksAnnotatedString = AnnotatedString(R.string.thanks_message, thanksXmlStyleOption)
thanksMessageTextView.text = thanksAnnotatedString.getStyledCharSequence(context)
```

### replacement:

```
<string name="thanks_message">Thanks <annotation replacement="username">usernamePlaceholder</annotation> for reading through the documentation.</string>
```

```
val username = "Yumi"
val thanksXmlStyleOption = XmlStyleOption(replacementList = listOf("username" to username)
val thanksAnnotatedString = AnnotatedString(R.string.thanks_message, thanksXmlStyleOption)
thanksMessageTextView.text = thanksAnnotatedString.getStyledCharSequence(context)
```

### custom stylization with [Spans](https://developer.android.com/reference/android/text/style/AbsoluteSizeSpan):

```
<string name="thanks_message"><annotation custom="bullet">Thanks <annotation custom="icon"> </annotation> for reading through the documentation.</annotation></string>
```

```
val thanksXmlStyleOption = XmlStyleOption(customAnnotations = listOf("bullet" to BulletSpan(), "icon" to ImageSpan(context, R.drawable.ic_user))
val thanksAnnotatedString = AnnotatedString(R.string.thanks_message, thanksXmlStyleOption)
thanksMessageTextView.text = thanksAnnotatedString.getStyledCharSequence(context)
```
