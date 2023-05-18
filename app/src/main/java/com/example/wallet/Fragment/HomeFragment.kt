package com.example.wallet.Fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wallet.Adapter.TransAdapter
import com.example.wallet.DBHelper.DBhelper
import com.example.wallet.Modal.homemodal
import com.example.wallet.databinding.FragmentHomeBinding
import com.example.wallet.databinding.UpdateItemBinding


class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var adapter: TransAdapter
    lateinit var dbHelper: DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        dbHelper = DBhelper(context)
        adapter = TransAdapter({

            dbHelper.deleteTransaction(it)
            adapter.update(dbHelper.getTransaction())

        }, {
            var dialog = Dialog(requireContext())
            var bind = UpdateItemBinding.inflate(layoutInflater)
            dialog.setContentView(bind.root)


            it.apply {
                bind.apply {
                    editAmt.setText(amt.toString())
                    editCart.setText(category)
                    editNote.setText(note)


                    btnUpdate.setOnClickListener {
                        var name2 = editName.text.toString()
                        var amt2 = editAmt.text.toString().toInt()
                        var category2 = editCart.text.toString()
                        var note2 = editNote.text.toString()

                        var modal = homemodal(id, name2, amt2, category2, note2, isExpense)

                        dbHelper.updateTransaction(modal)
                        adapter.update(dbHelper.getTransaction())
                        dialog.dismiss()
                    }
                }
            }
            dialog.show()


            binding = FragmentHomeBinding.inflate(layoutInflater)

            updatedashboard()


            return binding.root


            adapter.addTrans(dbHelper.getTransaction())

            binding.rcvlist.layoutManager = LinearLayoutManager(context)
            binding.rcvlist.adapter = adapter

            return binding.root
        }


    })




        fun updatedashboard() {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        dbHelper = DBhelper(context)
        var transList = dbHelper.getTransaction()
        var totalIncome = 0
        var totalExpense = 0

        for (addTrans in transList) {

            if (addTrans.isExpense == 0) {
                totalIncome += addTrans.amt
            } else if (addTrans.isExpense == 1) {
                totalExpense += addTrans.amt
            }

        }

        var totalbalance = (totalIncome - totalExpense).toString()

//        binding.income.text = totalIncome.toString().toInt().toString()
//        binding.expense.text = totalExpense.toString().toInt().toString()
        binding.amt.text = totalbalance.toInt().toString()


    }






        }





