package id.prasetiyo.RestClient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener{

    private Button btn_OkHTTP,btn_Retrofit,btn_qr,btn_fetch_github;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        btn_OkHTTP = (Button) view.findViewById(R.id.btn_OkHTTP);
        btn_Retrofit= (Button) view.findViewById(R.id.btn_Retrofit);
        btn_qr= (Button) view.findViewById(R.id.btn_qr);
        btn_fetch_github= (Button) view.findViewById(R.id.btn_fetch_github);

        btn_OkHTTP.setOnClickListener(this);
        btn_Retrofit.setOnClickListener(this);
        btn_qr.setOnClickListener(this);
        btn_fetch_github.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i;
        switch (id){
            case R.id.btn_OkHTTP:
                i = new Intent(getContext(),OkHTTP.class);
                startActivity(i);
                break;
            case R.id.btn_Retrofit:
                i = new Intent(getContext(),Retrofit.class);
                startActivity(i);
                break;
            case R.id.btn_qr:
                //i = new Intent(getContext(),QRScannerActivity.class);
                i = new Intent(getContext(),SimpleScannerActivity.class);
                startActivity(i);
                break;
            case R.id.btn_fetch_github:
                //i = new Intent(getContext(),QRScannerActivity.class);
                i = new Intent(getContext(),FetchGithubActivity.class);
                startActivity(i);
                break;
        }
    }
}
