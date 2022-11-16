package by.mishastoma.labsevenmobile;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Entity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<String> operationSys = new ArrayList<>();
    private File myExternalFile;
    private String filename = "file.txt";
    private File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View v, Bundle b) {

        List<String> data = new ArrayList<>();
        super.onViewCreated(v, b);
        prepareDataList(data);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, data);
        ListView someList = (ListView) v.findViewById(R.id.someList);
        someList.setAdapter(adapter);
        someList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayList<String> dataDetails = new ArrayList<String>();
                        prepareDetailsList(dataDetails);
                        Bundle result = new Bundle();
                        result.putString("titleKey", data.get(i));
                        result.putString("detailsKey", dataDetails.get(i));
                        getParentFragmentManager().setFragmentResult("requestKey", result);
                    }
                }
        );
        Button button5 = (Button) v.findViewById(R.id.button5);
        button5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        operationSys.clear();
                        getDataFromFile1();
                        onViewCreated(v, b);
                    }
                }
        );
        Button button6 = (Button) v.findViewById(R.id.button6);
        button6.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        operationSys.clear();
                        getDataFromFile2();
                        onViewCreated(v, b);
                    }
                }
        );

        Button button7 = (Button) v.findViewById(R.id.button7);
        button7.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        operationSys.clear();
                        getDataFromFile3();
                        onViewCreated(v, b);
                    }
                }
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFile2();
        createFile3();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    private void prepareDataList(List<String> data) {
        for (String text : operationSys) {
            String[] subStr = text.split("`");
            data.add(subStr[0] + ' ');
        }
    }

    private void prepareDetailsList(List<String> data) {
        for (String text : operationSys) {
            String[] subStr = text.split("`");
            data.add(subStr[1]);
        }
    }

    private void getDataFromFile1() {
        try {
            AssetManager am = getContext().getAssets();
            InputStream is = am.open("file.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                operationSys.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFile2() {
        String filename = "stroke.txt";
        String data = "2Windows`XP\n" +
                "MacOS`12 beta\n" +
                "Linux`Ubuntu\n" +
                "Android`13 beta\n" +
                "iOS`9";
        FileOutputStream fos;
        try {
            fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);
            OutputStreamWriter ows = new OutputStreamWriter(fos);
            ows.write(data);
            ows.close();
            fos.close();
        } catch (Exception e) {
            Log.e("FCK", e.toString());
            e.printStackTrace();
        }

    }

    private void getDataFromFile2() {
        try {
            FileInputStream fileIn = getContext().openFileInput("stroke.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[100];
            String s = "";
            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            operationSys = new ArrayList<String>(Arrays.asList(s.split("\n")));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getDataFromFile3() {
        try{
        File file = new File(folder, "file.txt");
        String data = getdata(file);
        operationSys = new ArrayList<String>(Arrays.asList(data.split("\n")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createFile3() {

        String data = "3Windows`XP\n" +
                "MacOS`12 beta\n" +
                "Linux`Ubuntu\n" +
                "Android`13 beta\n" +
                "iOS`9";

        try {
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


            File file = new File(folder, "file.txt");
            writeTextData(file, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}