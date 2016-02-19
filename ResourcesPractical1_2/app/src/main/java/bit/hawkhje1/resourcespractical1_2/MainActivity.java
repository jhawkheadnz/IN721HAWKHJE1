package bit.hawkhje1.resourcespractical1_2;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] febFridays = getFebFridays();

        TextView txtFebFridays = (TextView)findViewById(R.id.txtFebFridays);
        txtFebFridays.append(" ");

        for(int i = 0; i < febFridays.length; i++)
        {
            txtFebFridays.append(Integer.toString(febFridays[i]));
            txtFebFridays.append(" ");
        }

    }

    public int[] getFebFridays()
    {
        Resources resourceResolver = getResources();
        return resourceResolver.getIntArray(R.array.FebFridays);
    }

}
