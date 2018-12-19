
package br.com.vandre.thestartupfest.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.vandre.thestartupfest.R;
import br.com.vandre.thestartupfest.app.Constantes;
import br.com.vandre.thestartupfest.modelo.Startup;

public class StartupActivity extends AppCompatActivity {

    ImageView ivLogo;
    TextView tvNome;
    TextView tvSegmento;
    TextView tvDescricao;
    RatingBar rbProposta;
    RatingBar rbApresentacao;
    RatingBar rbDesenvolvimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_startup);
        iniciar();
    }

    private void declarar() {
        ivLogo = findViewById(R.id.item_startup_ivLogo);
        tvNome = findViewById(R.id.item_startup_tvNome);
        tvSegmento = findViewById(R.id.item_startup_tvSegmento);
        tvDescricao = findViewById(R.id.startup_tvDescricao);
        rbProposta = findViewById(R.id.startup_rbProposta);
        rbApresentacao = findViewById(R.id.startup_rbApresentacao);
        rbDesenvolvimento = findViewById(R.id.startup_rbDesenvolvimento);

    }

    private void iniciar() {
        declarar();
        Startup startup = getIntentStartup();

        Picasso.get()
                .load(startup.getImageUrl())
                .placeholder(R.color.colorAccent)
                .error(R.color.black_overlay)
                .into(ivLogo);

        tvNome.setText(startup.getName());
        tvSegmento.setText(startup.getSegmento().getName());
        tvDescricao.setText(startup.getDescription());

        rbProposta.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                votar(rbProposta, v, event);
                return true;
            }
        });

        rbApresentacao.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                votar(rbApresentacao, v, event);
                return true;
            }
        });

        rbDesenvolvimento.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                votar(rbDesenvolvimento, v, event);
                return true;
            }
        });
    }

    public void votar(final RatingBar ratingBar, final View view, final MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            float touchPositionX = motionEvent.getX();
            float width = ratingBar.getWidth();
            float starsf = (touchPositionX / width) * 5.0f;
            int stars = (int) starsf + 1;

            if (ratingBar.getRating() == 1 && stars == 1) {
                stars = 0;
            }
            ratingBar.setRating(stars);

            view.setPressed(false);
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setPressed(true);
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            view.setPressed(false);
        }
    }


    private Startup getIntentStartup() {
        Intent intent = getIntent();
        Startup startup;
        if (intent != null) {
            if (getIntent().getSerializableExtra(Constantes.STARTUP) != null) {
                startup = (Startup) getIntent().getSerializableExtra(Constantes.STARTUP);
                return startup;
            }
        }
        finish();
        return null;

    }

}
