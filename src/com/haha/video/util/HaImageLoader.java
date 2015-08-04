package com.haha.video.util;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.haha.common.config.HaConfig;
import com.haha.common.config.HaConfig.ConfigID;
import com.haha.common.logger.Logcat;
import com.haha.video.R;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class HaImageLoader {
	private static final String TAG = "ImageLoader";

	private final static DisplayImageOptions focusOptions = getDisplayImageOptions(R.drawable.ic_cycle_default);
	//private final static DisplayImageOptions stillOptions = getDisplayImageOptions(R.drawable.icon_template_still_default);
	//private final static DisplayImageOptions posterOptions = getDisplayImageOptions(R.drawable.icon_template_poster_default);


	public static void init(Context context, String cacheDir) {
		try {
			ImageLoader.getInstance().init(getImageLoaderConfig(context, cacheDir));
		} catch (Exception e) {
			Logcat.d(TAG, "ImageLoader init:"+e.getMessage());
		}
	}
	
	public static ImageLoaderConfiguration getImageLoaderConfig(Context context, String cacheDir){
		ImageLoaderConfiguration config = null;
	
		try{
			config = new ImageLoaderConfiguration.Builder(context)
					.threadPoolSize(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_LOAD_THREAD_SIZE))
					.denyCacheImageMultipleSizesInMemory()
					.memoryCache(new LRULimitedMemoryCache(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_MEMORY_CACHE_SIZE) * 1024 * 1024))
					.memoryCacheSize(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_MEMORY_CACHE_SIZE) * 1024 * 1024)
					.diskCache(new LruDiskCache(new File(cacheDir), new Md5FileNameGenerator(), HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_DISK_CACHE_SIZE) * 1024 * 1024))
					.diskCacheSize(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_DISK_CACHE_SIZE) * 1024 * 1024)
					.build();
		}catch(Exception e){
			config = new ImageLoaderConfiguration.Builder(context)
			.threadPoolSize(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_LOAD_THREAD_SIZE))
			.denyCacheImageMultipleSizesInMemory()
			.memoryCache(new LRULimitedMemoryCache(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_MEMORY_CACHE_SIZE) * 1024 * 1024))
			.memoryCacheSize(HaConfig.getInstance().getInt(ConfigID.WIDGET_IAMGELOADER_MEMORY_CACHE_SIZE) * 1024 * 1024)
			.build();			
		}
		
		return config;
	}

	public static DisplayImageOptions getDisplayImageOptions(int resId) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.showImageOnLoading(resId)
				.showImageForEmptyUri(resId)
				.showImageOnFail(resId)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565);
		return builder.build();
	}

	/**
	 * 加载没有默认图的imageview
	 * @param url
	 * @param imageView
	 */
	public static void displayNoDef(String url, ImageView imageView){
		displayImage(url, imageView);
	}

	/**
	 * 焦点图
	 * @param url
	 * @param imageView
	 */
	public static void displayFocus(String url, ImageView imageView) {
		displayImage(url, imageView, focusOptions);
	}

	public static void displayImage(String url, ImageView imageView) {
		try {
			ImageLoader.getInstance().displayImage(url, imageView);
		} catch (Throwable e) {
		}
	}

	public static void displayImage(String uri, ImageView imageView, DisplayImageOptions options){
		try {
			ImageLoader.getInstance().displayImage(uri, imageView, options);
		} catch (Throwable e) {
		}
	}
	
	public static Bitmap getShortCutBmp(Context ctx, String url, int def){
		try {
			return ImageLoader.getInstance().loadImageSync(url, getDisplayImageOptions(def));
		} catch (Throwable e) {
			try {
				return BitmapFactory.decodeResource(ctx.getResources(), def);
			} catch (Throwable e1) {
				return null;
			}
		}
	}
	public static void onDestroy() {
		ImageLoader.getInstance().destroy();
	}
}
