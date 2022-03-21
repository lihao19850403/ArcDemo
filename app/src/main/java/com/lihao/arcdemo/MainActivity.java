package com.lihao.arcdemo;

import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.lihao.arcdemo.presenter.DiariesPresenter;
import com.lihao.arcdemo.views.DiariesController;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Router mRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaries);
        mRouter = Conductor.attachRouter(this, (ViewGroup) findViewById(R.id.content), savedInstanceState);
        if (!mRouter.hasRootController()) {
            DiariesController controller = new DiariesController();
            controller.setPresenter(new DiariesPresenter(controller));
            mRouter.setRoot(RouterTransaction.with(controller));
        }
    }

    @Override
    public void onBackPressed() {
        if (!mRouter.handleBack()) {
            super.onBackPressed();
        }
    }
}