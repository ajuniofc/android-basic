package br.com.android.androidbasico.agenda.application.mapa;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.android.androidbasico.agenda.database.AlunoDAO;
import br.com.android.androidbasico.agenda.model.Aluno;

/**
 * Created by JHUNIIN on 21/01/2018.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicaoInicial = pegaCoordenadaDoEndereco("Gama-DF");
        if (posicaoInicial != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoInicial, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO dao = new AlunoDAO(getContext());
        for (Aluno aluno: dao.buscarAlunos()) {
            LatLng coordenadas = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if (coordenadas != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenadas);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        dao.close();

        Localizador localizador = new Localizador(getContext(), googleMap);
        localizador.start();
    }

    private LatLng pegaCoordenadaDoEndereco(String endereco) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> resultados;
        try {
            resultados = geocoder.getFromLocationName(endereco, 1);

            if (!resultados.isEmpty()){
                LatLng latLng = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
                return latLng;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
