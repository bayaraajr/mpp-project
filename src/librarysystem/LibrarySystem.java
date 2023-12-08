package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;

import static dataaccess.Auth.*;


public class LibrarySystem extends JFrame implements LibWindow {

    ControllerInterface ci = new SystemController();
    public final static LibrarySystem INSTANCE = new LibrarySystem();
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu options;
    JMenuItem login, allBookIds, allMemberIds, checkoutBook, newMember, addBookCopy, addBook;
    String pathToImage;
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LibrarySystem.INSTANCE,
            LoginWindow.INSTANCE,
            NewMemberWindow.INSTANCE,
            CheckoutBookWindow.INSTANCE,
            AllMemberIdsWindow.INSTANCE,
            AllBookIdsWindow.INSTANCE,
            AddBookCopyWindow.INSTANCE,
            AddBookWindow.INSTANCE,
            AddAuthorWindow.INSTANCE
    };

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
        }
    }

    private LibrarySystem() {
    }

    public void init() {
        formatContentPane();
        setPathToImage();
        insertSplashImage();

        createMenus();
        //pack();
        setSize(660, 500);
        isInitialized = true;
    }

    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        Path fullPath = Paths.get(currDirectory, "src", "librarysystem", "library.jpg");
        pathToImage = fullPath.toString();
    }

    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }

    private void createMenus() {
        menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
        addMenuItems();
        setJMenuBar(menuBar);
    }

    void addMenuItems() {

        if (options != null)
            menuBar.remove(options);

        options = new JMenu("Options");
        menuBar.add(options);

        login = new JMenuItem(SystemController.currentAuth != null ? "Logout" : "Login");
        login.addActionListener(new LoginListener());

        addBook = new JMenuItem("Add new Book");
        addBook.addActionListener(new addBookListener());

        checkoutBook = new JMenuItem("Checkout");
        checkoutBook.addActionListener(new CheckoutListener());

        addBookCopy = new JMenuItem("Add Book Copy");
        addBookCopy.addActionListener(new addBookCopyListener());

        newMember = new JMenuItem("New Member");
        newMember.addActionListener(new NewMemberListener());

        allBookIds = new JMenuItem("Book list");
        allBookIds.addActionListener(new AllBookIdsListener());

        allMemberIds = new JMenuItem("Member list");
        allMemberIds.addActionListener(new AllMemberIdsListener());

        options.add(login);

        if (SystemController.currentAuth != null) {
            if(SystemController.currentAuth == BOTH){
                options.add(checkoutBook);
                options.add(newMember);
                options.add(addBookCopy);
                options.add(addBook);

            }else if (SystemController.currentAuth == LIBRARIAN) {
                options.add(checkoutBook);
            } else if (SystemController.currentAuth == ADMIN) {
                options.add(newMember);
                options.add(addBookCopy);
                options.add(addBook);
            }
            options.add(allBookIds);
            options.add(allMemberIds);
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //Checking Rules
            if (SystemController.currentAuth == null) {
                System.out.println("User is logging in");
            } else {
                System.out.println("User is logging out");
                SystemController.currentAuth = null;
                addMenuItems();
            }

            LibrarySystem.hideAllWindows();
            if(!LoginWindow.INSTANCE.isInitialized())
                LoginWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
            LoginWindow.INSTANCE.setVisible(true);

        }

    }

    class addBookCopyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            if(!AddBookCopyWindow.INSTANCE.isInitialized())
                AddBookCopyWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AddBookCopyWindow.INSTANCE);
            AddBookCopyWindow.INSTANCE.setVisible(true);
            AddBookCopyWindow.INSTANCE.pack();
        }

    }

    class addBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            if(!AddBookWindow.INSTANCE.isInitialized())
                AddBookWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
            AddBookWindow.INSTANCE.setVisible(true);
            AddBookWindow.INSTANCE.pack();
        }

    }



    class CheckoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            if(!CheckoutBookWindow.INSTANCE.isInitialized())
                CheckoutBookWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(CheckoutBookWindow.INSTANCE);
            CheckoutBookWindow.INSTANCE.setVisible(true);
            CheckoutBookWindow.INSTANCE.pack();
        }

    }

    class NewMemberListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();

            if(!NewMemberWindow.INSTANCE.isInitialized())
                NewMemberWindow.INSTANCE.init();

            Util.centerFrameOnDesktop(NewMemberWindow.INSTANCE);
            NewMemberWindow.INSTANCE.setVisible(true);
        }

    }

    class AllBookIdsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            goToAllBooksDisplayPage();
        }
    }

    public void goToAllBooksDisplayPage(){
        LibrarySystem.hideAllWindows();
        if(!AllBookIdsWindow.INSTANCE.isInitialized())
            AllBookIdsWindow.INSTANCE.init();
        AllBookIdsWindow.INSTANCE.init();

        List<String> ids = ci.allBookIds();
        Collections.sort(ids);
        StringBuilder sb = new StringBuilder();
        for (String s : ids) {
            sb.append(s + "\n");
        }
        System.out.println(sb.toString());
//            AllBookIdsWindow.INSTANCE.setData(sb.toString());
        AllBookIdsWindow.INSTANCE.pack();
        //AllBookIdsWindow.INSTANCE.setSize(660,500);
        Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
        AllBookIdsWindow.INSTANCE.setVisible(true);
    }

    class AllMemberIdsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            if(!AllMemberIdsWindow.INSTANCE.isInitialized())
                AllMemberIdsWindow.INSTANCE.init();

            AllMemberIdsWindow.INSTANCE.pack();
            AllMemberIdsWindow.INSTANCE.setVisible(true);


            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.INSTANCE.init();

            List<String> ids = ci.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
                sb.append(s + "\n");
            }
            System.out.println(sb.toString());
            AllMemberIdsWindow.INSTANCE.setData(sb.toString());
            AllMemberIdsWindow.INSTANCE.pack();
            //AllMemberIdsWindow.INSTANCE.setSize(660,500);
            Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
            AllMemberIdsWindow.INSTANCE.setVisible(true);


        }

    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }


    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

}
