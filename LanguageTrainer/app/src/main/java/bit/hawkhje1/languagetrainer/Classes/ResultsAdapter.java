package bit.hawkhje1.languagetrainer.Classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bit.hawkhje1.languagetrainer.Enums.Article;
import bit.hawkhje1.languagetrainer.R;

public class ResultsAdapter extends ArrayAdapter<AnswerWord> {

    public ResultsAdapter(Context context, int resource, List<AnswerWord> objects) {
        super(context, resource, objects);



    }

    // http://developer.android.com/reference/android/widget/ArrayAdapter.html#getView(int, android.view.View, android.view.ViewGroup)
    // View Holder Pattern: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // use view holder pattern
        ViewHolder viewHolder = null;

        if(convertView == null) {

            // http://developer.android.com/reference/android/view/LayoutInflater.html#inflate(int, android.view.ViewGroup, boolean)
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.review_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.imgWordImage = (ImageView) convertView.findViewById(R.id.result_imgWordImage);
            viewHolder.txtGender = (TextView) convertView.findViewById(R.id.result_txtGender);
            viewHolder.txtArticle = (TextView) convertView.findViewById(R.id.result_txtArticle);
            viewHolder.txtNoun = (TextView) convertView.findViewById(R.id.result_txtNoun);
            viewHolder.txtEnglish = (TextView) convertView.findViewById(R.id.result_txtEnglish);


            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // get the result information from the current position
        AnswerWord answerWord = getItem(position);
        Word currentWord = answerWord.getWord();
        Article articleAnswer = answerWord.getArticleAnswer();

        // set the values for the items
        viewHolder.imgWordImage.setImageResource(currentWord.getImage());
        viewHolder.txtGender.setText(currentWord.getGender().toString());

        if (!currentWord.containsArticle(articleAnswer)) {
            String answerText = String.format("%s (Your answer %s)", currentWord.getArticle(), articleAnswer);
            viewHolder.txtArticle.setText(answerText);
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.reviewBGItemColorIncorrect));
        } else {
            viewHolder.txtArticle.setText(currentWord.getArticle().toString());
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.reviewBGItemColorCorrect));
        }

        viewHolder.txtNoun.setText(currentWord.getNoun());
        viewHolder.txtEnglish.setText(currentWord.getEnglish_translation());



        return convertView;
    }

    static class ViewHolder {

        ImageView imgWordImage;
        TextView txtGender;
        TextView txtArticle;
        TextView txtNoun;
        TextView txtEnglish;

    }
}
