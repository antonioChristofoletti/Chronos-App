package chronos.chronos.View.Principal;

import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import chronos.chronos.R;

public class ActPrincipal extends AppCompatActivity {

    TabLayout tablayoutPrincipal;

    ViewPager viewPager;

    PagerAdapter pagerAdapter;
    TabItem tabLayoutOS;
    TabItem tabLayoutCadastros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarActPrincipal);
        setSupportActionBar(toolbar);

        configurarComponentes();
    }

    public void configurarComponentes(){

        tablayoutPrincipal = (TabLayout) findViewById(R.id.tabLayoutPrincipal);

        tabLayoutOS = findViewById(R.id.tabOS);
        tabLayoutCadastros =  findViewById(R.id.tabCadastros);

        viewPager = (ViewPager) findViewById(R.id.viewPagerActPrincipal);

        pagerAdapter = new chronos.chronos.View.Principal.PagerAdapter(getSupportFragmentManager(), tablayoutPrincipal.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayoutPrincipal));

        tablayoutPrincipal.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //region EVENTOS MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_act_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    //endregion
}