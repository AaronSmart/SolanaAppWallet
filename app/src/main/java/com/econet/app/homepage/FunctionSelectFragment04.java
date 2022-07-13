package com.econet.app.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.econet.app.gamefi.Game2048Activity;
import com.econet.app.gamefi.H5hybridActivity;
import com.econet.app.listviewsolana.PopularTokenSearchActivity;
import com.econet.app.listviewsolana.SplTokenListActivity;
import com.econet.app.solwallet.R;
import com.econet.app.uitl.CustomToast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FunctionSelectFragment04 extends BaseFragment{
    private Unbinder unbinder;

    @BindView(R.id.gv_scene_one)
    SelectGridView mGvSceneOne;
    @BindView(R.id.gv_scene_two)
    SelectGridView mGvSceneTwo;
    @BindView(R.id.gv_scene_three)
    SelectGridView mGvSceneThree;
    @BindView(R.id.gv_scene_four)
    SelectGridView mGvSceneFour;
    @BindView(R.id.gv_scene_five)
    SelectGridView mGvSceneFive;
    @BindView(R.id.gv_scene_six)
    SelectGridView mGvSceneSix;
    @BindView(R.id.gv_scene_seven)
    SelectGridView mGvSceneSeven;
    @BindView(R.id.gv_scene_eight)
    SelectGridView mGvSceneEight;
    @BindView(R.id.gv_scene_nine)
    SelectGridView mGvSceneNine;
    @BindView(R.id.gv_scene_ten)
    SelectGridView mGvSceneTen;
    @BindView(R.id.gv_scene_eleven)
    SelectGridView mGvSceneEleven;
    HashMap<String, Integer> g = getMipmap();

    public static HashMap<String, Integer> getMipmap() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Tokens", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("GameFi", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("Hybrid", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp4", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp5", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp6", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp7", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp8", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp9", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp10", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp11", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp12", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp13", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp14", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp15", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp16", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp17", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp18", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp19", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp20", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp21", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp22", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp23", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp24", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp25", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp26", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp27", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp28", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp29", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp30", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp31", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp32", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp33", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp34", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp35", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp36", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp37", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp38", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp39", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp40", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp41", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp42", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp43", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp44", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp45", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp46", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp47", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp48", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp49", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp50", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp51", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp52", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp53", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp54", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        hashMap.put("dapp55", Integer.valueOf(com.econet.app.solwallet.R.mipmap.home_page_jcj));
        return hashMap;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mod001_activity_home_page5, container, false);
        unbinder= ButterKnife.bind(this,rootView);
        initWidget();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    void initWidget() {
        initSceneOne();
        initSceneTwo();
        initSceneThree();
        initSceneFour();
        initSceneFive();
        initSceneSix();
        initSceneSeven();
        initSceneEight();
        initSceneNine();
        initSceneTen();
        initSceneEleven();
    }

    private void initSceneOne() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"Tokens", "GameFi", "Hybrid", "dapp4", "dapp5"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneOne.setAdapter(simpleAdapter);
        mGvSceneOne.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }
    private void initSceneTwo() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp6", "dapp7", "dapp8", "dapp9", "dapp10"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneTwo.setAdapter(simpleAdapter);
        mGvSceneTwo.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }
    private void initSceneThree() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp11", "dapp12", "dapp13", "dapp14", "dapp15"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneThree.setAdapter(simpleAdapter);
        mGvSceneThree.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneFour() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp16", "dapp17", "dapp18", "dapp19", "dapp20"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneFour.setAdapter(simpleAdapter);
        mGvSceneFour.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneFive() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp21", "dapp22", "dapp23", "dapp24", "dapp25"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneFive.setAdapter(simpleAdapter);
        mGvSceneFive.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneSix() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp26", "dapp27", "dapp28", "dapp29", "dapp30"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneSix.setAdapter(simpleAdapter);
        mGvSceneSix.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneSeven() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp31", "dapp32", "dapp33", "dapp34", "dapp35"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneSeven.setAdapter(simpleAdapter);
        mGvSceneSeven.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneEight() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp36", "dapp37", "dapp38", "dapp39", "dapp40"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneEight.setAdapter(simpleAdapter);
        mGvSceneEight.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneNine() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp41", "dapp42", "dapp43", "dapp44", "dapp45"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneNine.setAdapter(simpleAdapter);
        mGvSceneNine.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneTen() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp46", "dapp47", "dapp48", "dapp49", "dapp50"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneTen.setAdapter(simpleAdapter);
        mGvSceneTen.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }

    private void initSceneEleven() {
        final ArrayList arrayList = new ArrayList();
        String[] strArr = {"dapp51", "dapp52", "dapp53", "dapp54", "dapp55"};
        for (int i = 0; i < strArr.length; i++) {
            HashMap hashMap = new HashMap();
            hashMap.put("imageUrl", g.get(strArr[i]));
            hashMap.put("text", strArr[i]);
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), arrayList, R.layout.item_function, new String[]{"imageUrl", "text"}, new int[]{R.id.imageview, R.id.textview});
        mGvSceneEleven.setAdapter(simpleAdapter);
        mGvSceneEleven.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
                String obj = ((HashMap) arrayList.get(i)).get("text").toString();
                itemClick(obj);
            }
        });
    }
    
    public void itemClick(String str) {
        if (str.equals("Tokens")) {
            //CustomToast.showToast(getContext(),"app is under construction");
            Intent i = new Intent(getActivity(), PopularTokenSearchActivity.class);
            startActivity(i);
//        } else if (str.equals("app2")) {
//           // startActivity(new Intent(getActivity(), DriverSearchActivity_.class));
//        }
         }else if (str.equals("GameFi"))
        {
            Intent i = new Intent(getActivity(), Game2048Activity.class);
            startActivity(i);
        }
//        else if (str.equals("Hybrid"))
//        {
//            Intent i = new Intent(getActivity(), H5hybridActivity.class);
//            startActivity(i);
//        }
//        else if (str.equals("Hybrid"))
//        {
//            Intent intent = new Intent(getActivity(), TransferCheckActivity.class);
//            Bundle bundle = new Bundle();
//            //iTd7nDbZmUXLeKwvKbszjN6gMjKZvddtoJ7imPeybWHGgN1MTosFeTuau5dupQkAvLe9PQCGCcTgP4ejXM6LR6w
//            //3PzCNgd5Zvi1iXyUg3WrFZNrhnqYpoANenpfvu5wGWKcT4ZSxz7uxQ9pNCUUQgjW277KwjqoEucFWRhksX8hSFu7
//            bundle.putSerializable("signature", "Bk8rJSgj4qjcZucvZPy37TnHUUVBHbr2mb6qMSsDVyVgcnmVn6asAXZ1Etpsr1FoYfU2GqPuyU1vSyrSf9iQge4");
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
        else
        {
            CustomToast.showToast(getContext(),"app is under construction");
        }
    }

    public static FunctionSelectFragment04 newInstance(int index) {
        FunctionSelectFragment04 f = new FunctionSelectFragment04();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }


}
