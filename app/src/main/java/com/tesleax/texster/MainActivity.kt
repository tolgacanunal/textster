package com.tesleax.texster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.BackgroundColorSpan
import android.text.style.BulletSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import androidx.core.content.ContextCompat
import com.tesleax.textster.AnnotatedString
import com.tesleax.textster.XmlStyleOption
import com.tesleax.textster.getStyledCharSequence
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Textster", measureTimeMillis {
            setupThanksTextView()
            setupWebsiteTextView()
            setupBulletTextView()
        }.toString())
    }

    private fun setupThanksTextView() {
        mainThanksTextView.text = AnnotatedString(
            resId = R.string.thanks_message,
            xmlStyleOption = XmlStyleOption(replacementList = listOf("username" to "user"))
        ).getStyledCharSequence(this)
    }

    private fun setupWebsiteTextView() {
        mainWebsiteTextView.text = AnnotatedString(
            resId = R.string.website_message,
            xmlStyleOption = XmlStyleOption(
                replacementList = listOf("githuburl" to GITHUB_URL)
            )
        ).getStyledCharSequence(this)
    }

    private fun setupBulletTextView() {
        mainBulletTextView.text = AnnotatedString(
            resId = R.string.bullet_message,
            xmlStyleOption = XmlStyleOption(
                replacementList = listOf(
                    "first_bullet_number" to "0"
                ),
                customAnnotations = listOf(
                    "bullet1" to BulletSpan(
                        15,
                        ContextCompat.getColor(this, R.color.red)
                    ),
                    "background0" to BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.teal_200)
                    ),
                    "background1" to BackgroundColorSpan(
                        ContextCompat.getColor(this, R.color.red_alpha10)
                    ),
                    "bullet_text_scale" to RelativeSizeSpan(1.5f)
                )
            )
        ).getStyledCharSequence(this)
    }

    companion object {
        private const val GITHUB_URL = "www.github.com/tolgacanunal"
    }
}
