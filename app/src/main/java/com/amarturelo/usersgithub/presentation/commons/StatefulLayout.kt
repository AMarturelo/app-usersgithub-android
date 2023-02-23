package com.amarturelo.usersgithub.presentation.commons

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import java.util.*

class StatefulLayout : FrameLayout {
    private val mStateViews: MutableMap<String, View?> =
        HashMap()
    private var mState: String? = null
    private var mOnStateChangeListener: OnStateChangeListener? = null
    private var mInitialized = false
    private var mDirtyFlag = false

    interface OnStateChangeListener {
        fun onStateChange(state: String?)
    }

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr) {
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (!mInitialized) onSetupContentState()
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(
            SAVED_INSTANCE_STATE,
            super.onSaveInstanceState()
        )
        saveInstanceState(bundle)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        var s: Parcelable? = state
        if (s is Bundle) {
            val bundle = s
            if (mState == null) restoreInstanceState(bundle)
            s = bundle.getParcelable(SAVED_INSTANCE_STATE)
        }
        super.onRestoreInstanceState(s)
    }

    fun setStateView(state: String, view: View?) {
        if (mStateViews.containsKey(state)) {
            removeView(mStateViews[state])
        }
        mStateViews[state] = view
        if (view!!.parent == null) {
            addView(view)
        }
        view.visibility = View.GONE
        mDirtyFlag = true
    }

    var state: String?
        get() = mState
        set(state) {
            state?:return
            if (mState != null && mState == state && !mDirtyFlag) return
            mState = state
            for (s in mStateViews.keys) {
                mStateViews[s]?.visibility = if (s == state) View.VISIBLE else View.GONE
            }
            if (mOnStateChangeListener != null) mOnStateChangeListener!!.onStateChange(state)
            mDirtyFlag = false
        }

    fun setOnStateChangeListener(listener: OnStateChangeListener?) {
        mOnStateChangeListener = listener
    }

    fun saveInstanceState(outState: Bundle) {
        if (mState != null) outState.putString(SAVED_STATE, mState)
    }

    fun restoreInstanceState(savedInstanceState: Bundle): String? {
        val s =
            savedInstanceState.getString(SAVED_STATE)
        state = s
        return state
    }

    fun getStateView(state: String?): View? {
        return mStateViews[state]
    }

    fun clearStates() {
        for (state in HashSet(mStateViews.keys)) {
            val view = mStateViews[state]
            if (state != State.CONTENT) {
                removeView(view)
                mStateViews.remove(state)
            }
        }
    }

    protected fun onSetupContentState() {
        check(childCount == 1 + mStateViews.size) { "Invalid child count. StatefulLayout must have exactly one child." }
        val contentView = getChildAt(mStateViews.size)
        removeView(contentView)
        setStateView(
            State.CONTENT,
            contentView
        )
        state = State.CONTENT
        mInitialized = true
    }

    object State {
        const val CONTENT = "content"
    }

    class StateController private constructor() {
        private val mStateMap: MutableMap<String, View> =
            HashMap()
        private var mState =
            State.CONTENT
        private var mListener: OnStateChangeListener? = null
        val states: Map<String, View>
            get() = mStateMap

        var state: String
            get() = mState
            set(newState) {
                mState = newState
                if (mListener != null) mListener!!.onStateChange(newState)
            }

        fun setOnStateChangeListener(listener: OnStateChangeListener?) {
            mListener = listener
        }

        class Builder {
            var mStateController = StateController()
            fun withState(
                state: String,
                stateView: View
            ): Builder {
                mStateController.mStateMap[state] = stateView
                return this
            }

            fun build(): StateController {
                return mStateController
            }
        }

        companion object {
            fun create(): Builder {
                return Builder()
            }
        }
    }

    companion object {
        const val SAVED_INSTANCE_STATE = "instanceState"
        private const val SAVED_STATE = "stateful_layout_state"
    }
}