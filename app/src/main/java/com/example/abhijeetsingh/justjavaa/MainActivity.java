package com.example.abhijeetsingh.justjavaa;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You can't have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You can't have less than 1 coffee", Toast.LENGTH_LONG).show();
            return;

        }
        quantity = quantity - 1;
        displayQantity(quantity);
    }

    /* This method is called when call button is clicked.
    *
    */
    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        String number= "+918574345660";
        intent.setData(Uri.parse("tel:" + number));
        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(intent);
         }
     }

    /* This method is called when submitOrder button is clicked.
       *
       */
    public void submitOrder(View view) {
       /*Find the users name.*/
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        /*Find the user address.*/
        EditText addressField =(EditText) findViewById(R.id.address_field);
        String address = addressField.getText().toString();

        /*Find the user mobile.*/
        EditText phoneField = (EditText) findViewById(R.id.phone_field);
        String phone = phoneField.getText().toString();

        /* figure out if the user wants whipped cream toppings*/

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        Boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        /* figure out if the user wants whipped cream toppings*/

        CheckBox chocoletsCheckBox = (CheckBox) findViewById(R.id.chocolets_checkbox);
        Boolean haschocolets = chocoletsCheckBox.isChecked();

        int price= calculatePrice(hasWhippedCream, haschocolets);
        String pricemessage =createOrderSummary(name, address, phone, price, hasWhippedCream, haschocolets);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:customercare@cafecoffeeday.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Please Deliver the Order At Following Address" );
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        displayMessage(pricemessage);
    }
    /**
     * * Calculate the price of the order.
     * @return total price
     *
    */
    private int calculatePrice(boolean whippedCreamPrice , boolean chocoletsPrice) {
        int baseprice=5;
        if (whippedCreamPrice) {
            baseprice = baseprice + 1;
        }
        if (chocoletsPrice) {
            baseprice = baseprice + 2;
        }
        return baseprice*quantity;
    }
    /* Create order summary
    * @param price of the order
     */


    private String createOrderSummary(String name, String address,String phone, int price, boolean addWhippedCream, boolean addChocolets)
    {
        String pricemessage = getString(R.string.order_summary_name,name)+ "\n"
                + getString(R.string.order_summary_address,address) + "\n"+ getString(R.string.order_summary_phone,phone);


        pricemessage += "\nAdd Whipped Cream:" + addWhippedCream;
        pricemessage += "\nAdd Whipped Cream:" + addChocolets;
        pricemessage += "\nQuantity:" + quantity;
        pricemessage +=  "\nTotal Price:₹" + price;
        pricemessage += "\n" + getString(R.string.thank_you);
        return pricemessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.tV_quantity);
        quantityTextView.setText(" " + numberOfCoffees);
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.tV_price);
        priceTextView.setText(message);
    }


}