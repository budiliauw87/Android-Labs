package dev.liau.nfc;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Event Clicklistener
        MaterialButton btnReader = (MaterialButton) findViewById(R.id.btnReader);
        btnReader.setOnClickListener(view -> startActivity(new Intent(this, NfcReaderActivity.class)));
        MaterialButton btnEmulation = (MaterialButton) findViewById(R.id.btnEmulation);
        btnEmulation.setOnClickListener(view -> {
            try {
//                testEncryptData();
                debugExe();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });

    }

    void debugExe() {
        Log.d("MainActivity", "tester");
        byte[] APDU = {0, -92, 4, 0, 11, -96, 0, 0, 0, 24, 15, 0, 0, 1, -128, 1, 0, -92, 1, 0, 2, 2, 0, 0, -80, -127, 0, -114};
        byte[] response = new byte[]{0, -92, 4, 0, 11, -96, 0, 0, 0, 24, 15, 0, 0, 1, -128, 1};
        Log.e(TAG, "First Log \n"+Utils.toHex(APDU));

        byte[] bArrCopyOfRange = Arrays.copyOfRange(APDU, 0, 16);
        Log.e(TAG, "Second Log \n"+Utils.toHex(bArrCopyOfRange));

        final byte[] f1341r = {-128, 50, 0, 3, 4, 0, 0, 0, 0, 0, -80, -124, 0, 60};

        byte[] bArrCopyOfRange10 = Arrays.copyOfRange(APDU, 23, 28);
        byte[] bArrCopyOfRange11 = Arrays.copyOfRange(f1341r, 0, 9);
        byte[] bArrCopyOfRange12 = Arrays.copyOfRange(APDU, 16, 23);

        Log.e(TAG, "Third Log \n"+Utils.toHex(bArrCopyOfRange10)
                + "\n"+Utils.toHex(bArrCopyOfRange11)
                + "\n"+Utils.toHex(bArrCopyOfRange12));


    }


    void testEncryptData() throws IOException {
        File file = new File(getCacheDir(), "s.dex");
        Log.e("test ecryptData", file.toString());
        if (file.exists()) {
            file.delete();
        }
        AssetManager assetManager = getAssets();
        InputStream inputStream =  assetManager !=null ? assetManager.open("s.txt") : null;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] bArrA = convertSecondPass(firstPass(inputStream));
        bufferedOutputStream.write(bArrA, 0, bArrA.length);
        bufferedOutputStream.close();
        inputStream.close();
    }
    public static byte[] firstPass(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i <= 0) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, i);
        }
    }


    public static byte[] convertSecondPass(byte[] bArr) {
        for (int i = 16; i < bArr.length; i++) {
            bArr[i] = (byte) (bArr[i] ^ 90);
        }
        return bArr;
    }

}