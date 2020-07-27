package com.tesleax.texster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainThanksTextView.text = getXmlStyledString(
            stringResId = R.string.thanks_message,
            annotationOption = AnnotationOption(
                replacementList = listOf("username" to "tesleax")
            )
        )
        val action2: (CharSequence) -> Unit = {
            startAction2()
        }
        mainWebsiteTextView.text = getXmlStyledString(
            stringResId = R.string.website_message,
            annotationOption = AnnotationOption(
                replacementList = listOf("githuburl" to GITHUB_URL),
                clickableList = listOf("action2" to action2)
            )
        )
        mainWebsiteTextView.movementMethod = LinkMovementMethod.getInstance()
        mainBulletTextView.text = getXmlStyledString(
            stringResId = R.string.bullet_message,
            annotationOption = AnnotationOption(
                customAnnotations = listOf(
                    "bullet0" to BulletSpan(),
                    "bullet1" to BulletSpan(),
                    "background0" to BackgroundColorSpan(ContextCompat.getColor(this, R.color.teal_200)),
                    "background1" to BackgroundColorSpan(ContextCompat.getColor(this, R.color.purple_500))
                )
            )
        )
    }

    private fun startAction2() {
        Toast.makeText(this, "Action 2 Clicked", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val GITHUB_URL = "www.github.com/tolgacanunal"
    }
}
