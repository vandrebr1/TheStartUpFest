package br.com.vandre.thestartupfest.atividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import br.com.vandre.thestartupfest.R;
import br.com.vandre.thestartupfest.app.Constantes;
import br.com.vandre.thestartupfest.modelo.Startup;
import br.com.vandre.thestartupfest.modelo.Voto;

public class StartupActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvNome;
    private TextView tvSegmento;
    private TextView tvDescricao;
    private RatingBar rbProposta;
    private RatingBar rbApresentacao;
    private RatingBar rbDesenvolvimento;
    private String id;
    DatabaseReference databaseArtists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_startup);
        iniciar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Voto voto = postSnapshot.getValue(Voto.class);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void declarar() {
        ivLogo = findViewById(R.id.item_startup_ivLogo);
        tvNome = findViewById(R.id.item_startup_tvNome);
        tvSegmento = findViewById(R.id.item_startup_tvSegmento);
        tvDescricao = findViewById(R.id.startup_tvDescricao);
        rbProposta = findViewById(R.id.startup_rbProposta);
        rbApresentacao = findViewById(R.id.startup_rbApresentacao);
        rbDesenvolvimento = findViewById(R.id.startup_rbDesenvolvimento);

        databaseArtists = FirebaseDatabase.getInstance().getReference("votos");

        id = id(StartupActivity.this);

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

            enviarVoto();

            view.setPressed(false);
        }
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setPressed(true);
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            view.setPressed(false);
        }

    }

    private void enviarVoto() {
        int votoProposta = (int) rbProposta.getRating();
        int votoApresentacao = (int) rbApresentacao.getRating();
        int votoDesenvolvimento = (int) rbDesenvolvimento.getRating();
        String startupNome = tvNome.getText().toString();
        Voto voto = new Voto(id, startupNome, votoProposta, votoApresentacao, votoDesenvolvimento);

        databaseArtists.child(id).child(startupNome).setValue(voto);

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

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public synchronized static String id(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }

}
