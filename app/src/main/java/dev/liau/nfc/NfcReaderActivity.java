package dev.liau.nfc;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import kotlin.UByte;

public class NfcReaderActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {

    private final String TAG = this.getClass().getSimpleName();
    private MaterialButton btnNFCSetting;
    private NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    private TextView tvInfo;
    private TextView tvTitle;
    public final byte[] MANDIRI_EMONEY = {77, 65, 78, 68, 73, 82, 73, 32, 69, 77, 79, 78, 69, 89};
    public final byte[] BCA_FLAZZ = {66, 67, 65, 32, 70, 76, 65, 90, 90};
    public final byte[] BNI_TAPCASH = {66, 78, 73, 32, 84, 65, 80, 67, 65, 83, 72};
    public final byte[] BRI_BRIZZI = {66, 82, 73, 32, 66, 82, 73, 90, 90, 73};
    public final byte[] MEGACASH = {77, 69, 71, 65, 67, 65, 83, 72};
    public final byte[] COMMUTER = {67, 79, 77, 77, 85, 84, 69, 82};
    public final byte[] EZLINK = {69, 90, 76, 73, 78, 75};

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.nfc.action.ADAPTER_STATE_CHANGED")) {
                int state = intent.getIntExtra("android.nfc.extra.ADAPTER_STATE", 1);
                switch (state) {
                    case 1:
                        btnNFCSetting.setVisibility(View.VISIBLE);
                        tvTitle.setText(NfcReaderActivity.this.getString(R.string.please_enable_nfc));
                        Log.e("NFCReader", "STATE OFF");
                        break;
                    case 2:
                        Log.e("NFCReader", "STATE TURNING ON");
                        break;
                    case 3:
                        btnNFCSetting.setVisibility(View.GONE);
                        tvTitle.setText(NfcReaderActivity.this.getString(R.string.please_tab_nfc));
                        Log.e("NFCReader", "STATE ON");
                        break;
                    case 4:
                        Log.e("NFCReader", "STATE TURNING OFF");
                        break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nfc_reader);
        resolveIntent(getIntent());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.btnNFCSetting = (MaterialButton) findViewById(R.id.btnNFCSetting);
        this.btnNFCSetting.setOnClickListener(view -> {
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        });
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvInfo = (TextView) findViewById(R.id.tvInfo);

        this.pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
        IntentFilter filter = new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED");
        registerReceiver(this.mReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkStatusNFC();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.nfcAdapter != null) {
            this.nfcAdapter.disableReaderMode(this);
            this.nfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mReceiver);
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        final StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        sb.append("ID (hex): ").append(toHex(id)).append('\n');
        sb.append("ID (reversed hex): ").append(toReversedHex(id)).append('\n');
        sb.append("ID (dec): ").append(toDec(id)).append('\n');
        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n');
        sb.append("Technologies: \n");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring("android.nfc.tech.".length())).append('\n');
        }
        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            try {
                isoDep.connect();
                isoDep.setTimeout(2000);

                byte[] APDU = {0, -92, 4, 0, 11, -96, 0, 0, 0, 24, 15, 0, 0, 1, -128, 1, 0, -92, 1, 0, 2, 2, 0, 0, -80, -127, 0, -114};
                byte[] APDU_FLAZZ = new byte[]{0, -92, 4, 0, 11, -96, 0, 0, 0, 24, 15, 0, 0, 1, -128, 1};

                byte[] response = isoDep.transceive(APDU_FLAZZ);

                String responseString = toHex(response);

                Log.e("NFCReader", "isoDep received " + responseString);

                if (responseString.equals("90 00")) {
                    sb.append("\nType Card : BCA Flazz");
                    final byte[] f1341r = {-128, 50, 0, 3, 4, 0, 0, 0, 0, 0, -80, -124, 0, 60};

                    byte[] bArrCopyOfRange10 = Arrays.copyOfRange(APDU, 23, 28); // numbercard
                    byte[] bArrCopyOfRange11 = Arrays.copyOfRange(f1341r, 0, 9);
                    byte[] bArrCopyOfRange12 = Arrays.copyOfRange(APDU, 16, 23);
                    byte[] bArrTransceive3 = isoDep.transceive(bArrCopyOfRange12);
                    byte[] bArrTransceive4 = isoDep.transceive(bArrCopyOfRange10);
                    byte[] bArrTransceive5 = isoDep.transceive(bArrCopyOfRange11);
                    int length3 = bArrTransceive4.length;
                    int length4 = bArrTransceive5.length;
                    int length5 = bArrTransceive3.length;

                    Log.e(TAG, "Amount: \n"+ ByteBuffer.wrap(bArrTransceive5).getInt());
                    if (bArrTransceive3[length5 - 2] == -112 && bArrTransceive3[length5 - 1] == 0 && bArrTransceive4[length3 - 2] == -112 && bArrTransceive4[length3 - 1] == 0) {
                        // numbercard  flazz
                        String strSubstring = new String(bArrTransceive4).substring(104, 120);

                        sb.append("\nNumber Card :"+ strSubstring.substring(0,4) +" "+ strSubstring.substring(4,8) +" "+ strSubstring.substring(8,12)+" "+strSubstring.substring(12,16) );
                        Log.e(TAG, "strSubstring \n"+strSubstring);
                        int amount = ByteBuffer.wrap(Arrays.copyOfRange(bArrTransceive5, 0, 4)).getInt();
                        Locale localeID = new Locale("in", "ID"); // "in" or "id" for Indonesian
                        NumberFormat idFormat = NumberFormat.getCurrencyInstance(localeID);
                        sb.append("\nAmount Card :"+ idFormat.format(amount));
                    }


//                    // 2. Read Record (where balance is stored)
//                    byte[] readBalance = {(byte) 0x00, (byte) 0xB2, (byte) 0x01, (byte) 0x0C, (byte) 0x00};
//                    byte[] balanceResponse = isoDep.transceive(readBalance);
//                    Log.e("NFCReader", "balanceResponse " + toHex(balanceResponse));
//                    if (balanceResponse.length >= 4) {
//                        // 3. Extract balance (usually first 4 bytes)
//                        // BCA Flazz stores balance in Big Endian
//                        long balance = ((balanceResponse[0] & 0xFFL) << 24) |
//                                ((balanceResponse[1] & 0xFFL) << 16) |
//                                ((balanceResponse[2] & 0xFFL) << 8) |
//                                (balanceResponse[3] & 0xFFL);
//
//                        sb.append("\nCard Type: BCA FLAZZ");
//                        sb.append("\nBalance: Rp ").append(String.format("%,d", balance));
//                    }
                } else {
                    sb.append("\nNot a Flazz Gen 2 card or error selecting AID.");
                }
                sb.append("\nCard Response (hex):\n").append(responseString);

                isoDep.close();
            } catch (IOException e) {
                Log.e("NFCReader", "Error reading IsoDep", e);
                sb.append("\nError reading card data.");
            }
        }
        runOnUiThread(() -> {
            tvInfo.setText(sb.toString());
        });


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resolveIntent(intent);
    }


    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        Log.e("NFCReader", "resolveIntent action is " + action);
        if ("android.nfc.action.TAG_DISCOVERED".equals(action)) {
            onTagDiscovered((Tag) intent.getParcelableExtra("android.nfc.extra.TAG"));
        }
    }

    private void checkStatusNFC() {
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (this.nfcAdapter == null) {
            this.tvTitle.setText("NFC not supported");
            return;
        }
        String nfcStatus = getString(this.nfcAdapter.isEnabled() ? R.string.please_tab_nfc : R.string.please_enable_nfc);
        this.tvTitle.setText(nfcStatus);
        int visible = this.nfcAdapter.isEnabled() ? View.GONE : View.VISIBLE;
        this.btnNFCSetting.setVisibility(visible);
        Bundle options = new Bundle();
        options.putInt("presence", ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.nfcAdapter.enableReaderMode(this, this, 135, options);
        this.nfcAdapter.enableForegroundDispatch(this, this.pendingIntent, null, null);
    }

    private String toHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            // Use String.format to ensure 2 characters per byte with leading zeros
            sb.append(String.format("%02X", bytes[i]));
            if (i < bytes.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String toReversedHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; i--) {
            sb.append(String.format("%02X", bytes[i]));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private long toDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (byte b : bytes) {
            long value = b & 255;
            result += value * factor;
            factor *= 256;
        }
        return result;
    }

    private long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; i--) {
            long value = bytes[i] & 255;
            result += value * factor;
            factor *= 256;
        }
        return result;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

}


