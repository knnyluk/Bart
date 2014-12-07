package com.internet.bart.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.internet.bart.R;
import com.internet.bart.models.AvailableItem;
import com.parse.ParseFile;

import java.io.ByteArrayOutputStream;

/**
 * Created on 12/5/14.
 */
public class CreateAvailableItemFragment extends Fragment implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private View rootView;
    private Bitmap thumbnailImage;
    private Button addPhotoButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_create_available_item, container ,false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addPhotoButton = (Button) rootView.findViewById(R.id.add_photo_button);
        addPhotoButton.setOnClickListener(this);

        Button createListingButton = (Button) rootView.findViewById(R.id.create_available_item_button);
        createListingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_photo_button:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.create_available_item_button:
                saveItemToParse(packageImage(thumbnailImage));
                getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            thumbnailImage = (Bitmap) extras.get("data");
            ImageView previewImageView = (ImageView) rootView.findViewById(R.id.preview_image_view);
            previewImageView.setImageBitmap(thumbnailImage);
            addPhotoButton.setText("retake photo");
        }
    }

    private void saveItemToParse(ParseFile thumbnail) {
        EditText newItemNameEditText = (EditText) rootView.findViewById(R.id.name_new_available_item_edit_text);
        EditText newItemTitleEditText = (EditText) rootView.findViewById(R.id.title_new_available_item_edit_text);
        EditText newItemFullDescriptionEditText = (EditText) rootView.findViewById(R.id.full_description_new_available_item_edit_text);

        AvailableItem.writeToParse(newItemNameEditText.getText().toString(),
                                   newItemTitleEditText.getText().toString(),
                                   newItemFullDescriptionEditText.getText().toString(),
                                   thumbnail);
    }

    private ParseFile packageImage (Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return new ParseFile("thumbnail.jpg", byteArray);
    }
}
