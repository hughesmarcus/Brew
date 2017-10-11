package com.nnc.hughes.brew.ui.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;

import com.nnc.hughes.brew.R;
import com.nnc.hughes.brew.data.models.Datum;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreweryDetailActivity extends AppCompatActivity {
    public static final String EXTRA_BREWERY_ID = "Brewery_ID";
    public static Datum brewery;
    @BindView(R.id.brewery_description)
    TextView description;
    @BindView(R.id.brewery_icon)
    ImageView icon;
    @BindView(R.id.brewery_name)
    TextView name;
    @BindView(R.id.brewery_website)
    TextView website;

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brewery_detail);
        setupWindowAnimations();
        ButterKnife.bind(this);
        Datum brewery = getIntent().getParcelableExtra(EXTRA_BREWERY_ID);
        if (brewery.getDescription() != null) {
            description.setText(brewery.getDescription());
        }

        name.setText(brewery.getName());
        website.setClickable(true);
        website.setText(fromHtml(brewery.getWebsite()));
        website.setMovementMethod(LinkMovementMethod.getInstance());
        if (brewery.getImages() != null) {
            Picasso.with(this).load(brewery.getImages().getIcon()).error(R.mipmap.ic_no_image).
                    placeholder(R.mipmap.ic_no_image)
                    .into(icon);
        } else {
            icon.setImageResource(R.mipmap.ic_no_image);
        }
    }

    private void setupWindowAnimations() {

        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
