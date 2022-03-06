package com.unique.blockchain.nft.view.fragment

//import com.space.exchange.biz.net.request.ZlmRequest
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.space.exchange.biz.common.base.BaseFragment
import com.unique.blockchain.nft.R
import com.unique.blockchain.nft.adapter.HelpItemAdapter
import kotlinx.android.synthetic.main.help_fragment.*

class HelpFragment : BaseFragment() {

    private val helpItemAdapter: HelpItemAdapter by lazy {
        HelpItemAdapter(R.layout.help_item_layout, mutableListOf())
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): HelpFragment {
            val helpFragment = HelpFragment()
            helpFragment.arguments = bundle
            return helpFragment
        }
    }

    override fun initView() {
        rl.layoutManager = LinearLayoutManager(context)
        rl.adapter = helpItemAdapter
    }

    override fun initData() {
        super.initData()
        val string = arguments?.getString("class_id")
        val mutableMapOf = mutableMapOf<String, String>()
        string?.let {
            mutableMapOf["class_id"] = string
        }
//        ZlmRequest
//                .getQuestionList(activity as RxAppCompatActivity, mutableMapOf)
//                .subscribe(object : ApiListener<QuestionListResponse>(activity) {
//                    override fun onSuccess(response: QuestionListResponse) {
//
//                    }
//
//                    override fun onFail(e: Throwable) {
//
//                    }
//
//                })
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
    }

    override fun getLayoutId(): Int {
        return R.layout.help_fragment
    }
}