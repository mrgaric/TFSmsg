package com.igordubrovin.tfsmsg.dialogs;

/**
 * Created by Ксения on 22.05.2017.
 */

public class DialogsPresenterTest {
    /*private final DBFlowHelper dbFlowHelperMock = mock(DBFlowHelper.class);
    private final IDialogsView iDialogsViewMock = mock(IDialogsView.class);
    private final DialogsPresenter dialogsPresenterSpy = spy(new DialogsPresenter(dbFlowHelperMock));

    @Before
    public void setUp(){
        doReturn(iDialogsViewMock).when(dialogsPresenterSpy).getView();
    }

    @Test
    public void when_attachView_and_dialogsItems_null_do_nothing(){
        dialogsPresenterSpy.attachView(iDialogsViewMock);
        verify(iDialogsViewMock, never()).showDialogs(anyList());
    }

    @Test
    public void when_attachView_and_dialogItems_not_null_then_showDialogs_and_clean_dialogItem(){
        List<DialogItem> dialogItems = dialogsPresenterSpy.dialogItems = anyList();
        dialogsPresenterSpy.attachView(iDialogsViewMock);
        verify(iDialogsViewMock).showDialogs(dialogItems);
        assertNull(dialogsPresenterSpy.dialogItems);
    }*/
}
