package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

new GetUrlDate().execute();
    }
    private class GetUrlDate  extends AsyncTask<String, String, String>
    {


        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection;
            BufferedReader bufferedReader;
            try {
                    URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/");
                connection =(HttpURLConnection) url.openConnection();
                connection.addRequestProperty("ID","524901");
                connection.addRequestProperty("APPID","c21566b1153f87e9f1d256b962cd6d42");
                OutputStream os = connection.getOutputStream();
                os.write("POST_PARAMS".getBytes());
                os.flush();
                os.close();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String s;
                String f = null;
                while ((s=bufferedReader.readLine())!= null)
                {
                    f=f+s;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private static void sendPOST() throws IOException {
        URL obj = new URL("""POST_URL""");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");

        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write("POST_PARAMS".getBytes());
        os.flush();
        os.close();
        // For POST only - END

        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("POST request not worked");
        }



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final String[] catNamesArray = {"Васька", "Рыжик", "Мурзик"};

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Выберите любимое имя кота")
                    // добавляем переключатели
                    .setSingleChoiceItems(catNamesArray, -1,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int item) {
                                    Toast.makeText(
                                            getActivity(),
                                            "Любимое имя кота: "
                                                    + catNamesArray[item],
                                            Toast.LENGTH_SHORT).show();
                                }
                            })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK, so save the mSelectedItems results somewhere
                            // or return them to the component that opened the dialog

                        }
                    })
                    .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            return builder.create();
        }



    }
}
