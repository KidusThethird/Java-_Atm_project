
package ATM_Project;

public class Withdrawal extends Transaction
 {
 private int amount; 
 private Keypad keypad; 
 private CashDispenser cashDispenser; 
 private final static int CANCELED = 6;
 public Withdrawal( int userAccountNumber, Screen atmScreen,
 BankDatabase atmBankDatabase, Keypad atmKeypad,
 CashDispenser atmCashDispenser )
 {
 super( userAccountNumber, atmScreen, atmBankDatabase );
 keypad = atmKeypad;
 cashDispenser = atmCashDispenser;
 }
 @Override
 public void execute()
 {
 boolean cashDispensed = false; 
 double availableBalance; 

 BankDatabase bankDatabase = getBankDatabase();
 Screen screen = getScreen();
 do
 {
 amount = displayMenuOfAmounts();

 if ( amount != CANCELED )
 {
 availableBalance =
 bankDatabase.getAvailableBalance( getAccountNumber() );
 if ( amount <= availableBalance )
 {if ( cashDispenser.isSufficientCashAvailable( amount ) )
 {
 bankDatabase.debit( getAccountNumber(), amount );
cashDispenser.dispenseCash( amount ); 
 cashDispensed = true; 
 screen.displayMessageLine( "\nYour cash has been" +
 " dispensed. Please take your cash now." );
 } 
 else 
 screen.displayMessageLine(
 "\nInsufficient cash available in the ATM." +
 "\n\nPlease choose a smaller amount." );
 } 
 else 
 {
 screen.displayMessageLine(
 "\nInsufficient funds in your account." +
 "\n\nPlease choose a smaller amount." );
 }
 } 
 else 
 {
 screen.displayMessageLine( "\nCanceling transaction..." );
 return;
 }
 } while ( !cashDispensed );

 } 
 private int displayMenuOfAmounts()
 {
 int userChoice = 0;

 Screen screen = getScreen(); // get screen reference
 int[] amounts = { 0, 50, 100, 200, 300, 500 };
 while ( userChoice == 0 )
 {
 screen.displayMessageLine( "\nWithdrawal Option:" );
 screen.displayMessageLine( "1 - 50 BIRR" );
 screen.displayMessageLine( "2 - 100 BIRR" );
 screen.displayMessageLine( "4 - 200 BIRR" );
 screen.displayMessageLine( "5 - 300 BIRR" );
 screen.displayMessageLine( "6 - Cancel transaction" );
 screen.displayMessage( "\nChoose a withdrawal amount: " );
 int input = keypad.getInput(); 
 switch ( input )
 {
     case 1:
 case 2: 
 case 3: 
 case 4:
 case 5:
 userChoice = amounts[ input ]; 
 break;
 case CANCELED:
 userChoice = CANCELED; 
 break;
 default: 
 screen.displayMessageLine("\nInvalid selection. Try again." );
 } 
 } 

 return userChoice;
 }
 }