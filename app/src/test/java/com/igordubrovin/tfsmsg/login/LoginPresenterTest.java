package com.igordubrovin.tfsmsg.login;

import com.igordubrovin.tfsmsg.mvp.iview.ILoginView;
import com.igordubrovin.tfsmsg.mvp.models.LoginModel;
import com.igordubrovin.tfsmsg.mvp.presenters.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by Игорь on 19.05.2017.
 */

public class LoginPresenterTest {

    private final LoginModel loginModel = mock(LoginModel.class);

    private final LoginPresenter loginPresenterSpy = spy(new LoginPresenter(loginModel));
    private final ILoginView viewMock = mock(ILoginView.class);

    @Before
    public void setUp() throws Exception {
        doReturn(viewMock).when(loginPresenterSpy).getView();
    }

    @Test
    public void when_attachView_and_result_null_do_nothing() throws Exception {
        loginPresenterSpy.attachView(viewMock);
        verify(loginPresenterSpy, never()).returnResultView(anyBoolean());
    }

    @Test
    public void when_attachView_and_result_not_null_call_returnResultView_and_clear_result() throws Exception {
        Boolean result = loginPresenterSpy.success = false;
        loginPresenterSpy.attachView(viewMock);
        verify(loginPresenterSpy).returnResultView(result);
        assertNull(loginPresenterSpy.success);
    }

    @Test
    public void given_viewAttached_when_setSuccess_then_returnResultView_with_result() throws Exception {
        Mockito.doReturn(true).when(loginPresenterSpy).isViewAttached();
        Boolean authorizationResult = true;
        loginPresenterSpy.setSuccess(authorizationResult);
        verify(loginPresenterSpy).returnResultView(authorizationResult);
    }

    @Test
    public void given_not_viewAttached_when_setSuccess_then_set_success() throws Exception {
        Mockito.doReturn(false).when(loginPresenterSpy).isViewAttached();
        Boolean authorizationResult = true;
        loginPresenterSpy.setSuccess(authorizationResult);
        assertNotNull(loginPresenterSpy.success);
    }

    @Test
    public void given_result_true_when_returnResultView_then_loginSuccessful() throws Exception {
        loginPresenterSpy.returnResultView(true);
        verify(viewMock).loginSuccessful();
    }

    @Test
    public void given_result_false_when_returnResultView_then_showError() throws Exception {
        loginPresenterSpy.returnResultView(false);
        verify(viewMock).showError();
    }

}
