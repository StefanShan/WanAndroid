package com.byte_stefan.login

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.byte_stefan.base.BaseResponse
import com.byte_stefan.login.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var mBindingView: FragmentLoginBinding? = null
    private lateinit var mViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: LoginViewModel by viewModels()
        mViewModel = viewModel
        mBindingView = FragmentLoginBinding.inflate(inflater)
        return mBindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBindingView?.tvRegister?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        mBindingView?.btnLogin?.setOnClickListener {

            val userName = mBindingView?.editLoginUsername?.text.toString()
            val password = mBindingView?.editLoginPassword?.text.toString()

            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "账号或密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            mViewModel.login(userName, password)
                .observe(this, Observer<BaseResponse<LoginResponse>> {
                    if (it.errorCode !=0 ) {
                        Toast.makeText(activity, "登录失败 ${it.errorCode}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "登录成功", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBindingView = null
    }
}