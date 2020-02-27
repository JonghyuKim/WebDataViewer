package com.hyu.webdataviewer.di

import android.view.ViewGroup
import com.hyu.webdataviewer.datasource.data.AmiiboData
import com.hyu.webdataviewer.datasource.parser.AmiiboDataParser
import com.hyu.webdataviewer.util.imageloader.IImageLoader
import com.hyu.webdataviewer.domain.model.IPreviewModel
import com.hyu.webdataviewer.datasource.parser.IDataParser
import com.hyu.webdataviewer.datasource.repository.AmiiboDataRepository
import com.hyu.webdataviewer.domain.datasource.IDataRepository
import com.hyu.webdataviewer.datasource.requester.*
import com.hyu.webdataviewer.domain.model.AmiiboModel
import com.hyu.webdataviewer.domain.usecase.GetAmiiboModelUseCase
import com.hyu.webdataviewer.domain.usecase.IUseCase
import com.hyu.webdataviewer.presentation.IMainActivityContract
import com.hyu.webdataviewer.presentation.MainPresenter
import com.hyu.webdataviewer.presentation.preview.PreviewPresenter
import com.hyu.webdataviewer.presentation.base.BaseListAdapter
import com.hyu.webdataviewer.presentation.base.BaseViewHolder
import com.hyu.webdataviewer.presentation.base.IBaseFragmentContract
import com.hyu.webdataviewer.presentation.datail.AmiiboViewFragment
import com.hyu.webdataviewer.presentation.datail.AmiiboViewPresenter
import com.hyu.webdataviewer.presentation.datail.IDetailViewContract
import com.hyu.webdataviewer.presentation.preview.IPreviewContract
import com.hyu.webdataviewer.presentation.preview.PreviewFragment
import com.hyu.webdataviewer.presentation.preview.list.*
import com.hyu.webdataviewer.util.imageloader.ImageGlide
import org.koin.core.qualifier.named
import org.koin.dsl.module

val amiiboModule = module{

    factory<IDataParser<String, List<AmiiboData>>> { AmiiboDataParser()}
    single<IDataRepository<AmiiboModel>> { AmiiboDataRepository() }
    factory<IUseCase<List<AmiiboModel>>> {GetAmiiboModelUseCase()}

    factory<IDetailViewContract.View> { AmiiboViewFragment() }
    factory<IDetailViewContract.Presenter> { (view : IDetailViewContract.View) -> AmiiboViewPresenter(view) }
}

val previewModule = module{
    //IPreviewContract
//    factory<IPreviewContract.View> { PreviewFragment() }
    factory<IPreviewContract.Presenter> { (view : IPreviewContract.View) -> PreviewPresenter(view) }

    //IPreviewItemContract
    factory<BaseListAdapter<IPreviewModel>> { PreviewAdaptor() }
    factory<IPreviewItemContract.Presenter> { (view : IPreviewItemContract.View) -> PreviewItemPresenter(view) }
    factory<BaseViewHolder<IPreviewModel>>(named(PreviewAdaptor.VIEW_TYPE_BASIC)) { (parentView : ViewGroup) ->
        PreviewItemHolder(
            parentView
        )
    }
    factory<BaseViewHolder<IPreviewModel>>(named(PreviewAdaptor.VIEW_TYPE_BIG_SIZE)) { (parentView : ViewGroup) ->
        PreviewItemBigSizeHolder(
            parentView
        )
    }
}

val baseModule = module {
    factory<IMainActivityContract.Presenter> { (view : IMainActivityContract.View) -> MainPresenter(view) }
    factory<IBaseFragmentContract.View> { PreviewFragment() }
}

val otherModule = module {
//    factory<IImageLoader>            { ImageDataProxy() }
//    factory<IDataRequester<String>>  { JsonFileRequester(get()) }
    single<IImageLoader>            { ImageGlide() }
    factory<IDataRequester<String>>  { OkHttpRequester() }
}