package com.internet.bart.activities;

import android.app.Activity;
import android.os.Bundle;

import com.internet.bart.R;
import com.internet.bart.fragments.OfferTradeListFragment;

/**
 * Created on 12/3/14.
 */
public class TradeProposalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_proposal);
        OfferTradeListFragment offerTradeListFragment = new OfferTradeListFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentcontainer, offerTradeListFragment)
                    .commit();
        }
    }
}
