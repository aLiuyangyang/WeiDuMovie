package com.bw.movie.view.fragment.guidepage_fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
/**
 * date:2019/1/24
 * author:刘洋洋(DELL)
 * function:引导页三
 */
public class GuideThirdFragment extends Fragment {
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_third,container,false);
      return view;
   }
}
