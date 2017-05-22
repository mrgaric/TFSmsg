package com.igordubrovin.tfsmsg.splash;

import com.igordubrovin.tfsmsg.mvp.iview.ISplashView;
import com.igordubrovin.tfsmsg.mvp.presenters.SplashPresenter;
import com.igordubrovin.tfsmsg.utils.LoginManager;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Ксения on 22.05.2017.
 */

public class SplashPresenterTest {
    private final LoginManager loginManagerMock = mock(LoginManager.class);
    private final SplashPresenter splashPresenterSpy = spy(new SplashPresenter(loginManagerMock));
    private final ISplashView viewMock = mock(ISplashView.class);

    @Before
    public void setUp(){
        doReturn(viewMock).when(splashPresenterSpy).getView();
    }

    @Test
    public void given_result_true_when_returnResultView_then_showNavigationActivity(){
        splashPresenterSpy.returnResultView(true);
        verify(viewMock).showNavigationActivity();
    }

    @Test
    public void given_result_false_when_returnResultView_then_showLoginActivity(){
        splashPresenterSpy.returnResultView(false);
        verify(viewMock).showLoginActivity();
    }

}
