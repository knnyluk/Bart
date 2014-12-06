package com.internet.bart.activities;

import android.app.Activity;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.OfferTradeListFragment;
import com.internet.bart.models.AvailableItem;

/**
 * Created on 12/3/14.
 */
public class TradeProposalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout_fragholder);

        AvailableItem currentItem = getIntent().getExtras().getParcelable(AvailableItem.ITEM_TO_TRADE_FOR);

        OfferTradeListFragment offerTradeListFragment = OfferTradeListFragment.newInstance(currentItem);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentcontainer, offerTradeListFragment)
                    .commit();
        }
    }
}
