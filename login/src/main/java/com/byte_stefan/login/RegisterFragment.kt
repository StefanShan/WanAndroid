package com.byte_stefan.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.byte_stefan.base.BaseResponse
import com.byte_stefan.login.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var bindingView: FragmentRegisterBinding? = null
    private lateinit var mViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: LoginViewModel by viewModels()
        mViewModel = viewModel
        bindingView = FragmentRegisterBinding.inflate(inflater)
        return bindingView?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView?.ivRegisterClose?.setOnClickListener {
            findNavController().navigateUp()
        }

        bindingView?.btnRegister?.setOnClickListener {
            val userName = bindingView?.edtRegisterUsername?.text.toString()
            val password = bindingView?.editRegisterPassword?.text.toString()
            val repassword = bindingView?.editRegisterRepassword?.text.toString()
            if (userName.isEmpty() || password.isEmpty() || repassword.isEmpty()){
                Toast.makeText(activity, "用户名或密码不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != repassword){
                Toast.makeText(activity, "两次输入的密码不相同", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mViewModel.register(userName, password, repassword).observe(this, Observer<BaseResponse<LoginResponse>> {
                if (it.errorCode != 0){
                    Toast.makeText(activity, "注册失败 ${it.errorCode}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity, "注册成功", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingView = null
    }
}