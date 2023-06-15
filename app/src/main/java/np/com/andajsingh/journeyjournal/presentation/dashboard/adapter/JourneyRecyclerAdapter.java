package np.com.andajsingh.journeyjournal.presentation.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import np.com.andajsingh.domain.models.Journey;
import np.com.andajsingh.journeyjournal.R;

/**
 * Created on 21/03/2022.
 */
public class JourneyRecyclerAdapter extends RecyclerView.Adapter<JourneyRecyclerAdapter.JourneyViewHolder> {
    private final List<Journey> journeys;
    private final Context context;
    private final JourneyRecyclerAdapterListener listener;

    public JourneyRecyclerAdapter(Context context, List<Journey> journeys, JourneyRecyclerAdapterListener listener) {
        this.journeys = journeys;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public JourneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_journey_item,
                parent,
                false
        );
        return new JourneyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JourneyViewHolder holder, int position) {
        if (journeys.isEmpty()) {
            return;
        }
        Journey individualJourney = journeys.get(position);
        holder.itemTitleTv.setText(individualJourney.getTitle());
        holder.itemDescriptionTv.setText(individualJourney.getDescription());
        holder.itemDateTv.setText("Date : ".concat(individualJourney.getDate()));
        File file = new File(individualJourney.getImagePath());
        Glide.with(context)
                .asBitmap()
                .load(file)
                .into(holder.itemImageView);
        holder.itemBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onJourneyEditButtonClicked(individualJourney);
            }
        });

        holder.itemBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onJourneyDeleteButtonClicked(individualJourney);
            }
        });

        holder.itemBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onJourneyShareButtonClicked(individualJourney);
            }
        });

    }

    @Override
    public int getItemCount() {
        return journeys.size();
    }

    public static class JourneyViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView itemImageView;
        private final AppCompatTextView itemTitleTv;
        private final AppCompatTextView itemDescriptionTv;
        private final AppCompatTextView itemDateTv;
        private final AppCompatImageButton itemBtnEdit;
        private final AppCompatImageButton itemBtnDelete;
        private final AppCompatImageButton itemBtnShare;

        public JourneyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_iv);
            itemTitleTv = itemView.findViewById(R.id.item_title_tv);
            itemDescriptionTv = itemView.findViewById(R.id.item_description_tv);
            itemDateTv = itemView.findViewById(R.id.item_date_tv);
            itemBtnEdit = itemView.findViewById(R.id.item_img_btn_edit);
            itemBtnDelete = itemView.findViewById(R.id.item_img_btn_delete);
            itemBtnShare = itemView.findViewById(R.id.item_img_btn_share);
        }
    }

    public interface JourneyRecyclerAdapterListener {
        void onJourneyEditButtonClicked(Journey journey);
        void onJourneyDeleteButtonClicked(Journey journey);
        void onJourneyShareButtonClicked(Journey journey);
    }
}
