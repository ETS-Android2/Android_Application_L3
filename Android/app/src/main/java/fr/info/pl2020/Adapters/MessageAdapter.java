package fr.info.pl2020.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.info.pl2020.R;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<String> messageList;
    private LayoutInflater layoutInflater;

    private class MessageHolder {
        private TextView text;
        MessageHolder(View v) {
            text = v.findViewById(R.id.message);
        }
    }

    public MessageAdapter(Context context, List<String> objects) {
        super();
        this.context = context;
        this.messageList = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.message, null);

        MessageHolder messageHolder = (MessageHolder) convertView.getTag();
        if (messageHolder == null) {
            messageHolder = new MessageHolder(convertView);
            convertView.setTag(messageHolder);
        }

        messageHolder.text.setText(this.messageList.get(position));

        return convertView;
    }
}
