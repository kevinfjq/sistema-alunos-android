package com.example.sistemalistview.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistemalistview.model.Aluno;
import com.example.sistemalistview.util.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlunoDAO {
    private ConnectionFactory conexao;
    private SQLiteDatabase banco;

    public AlunoDAO(Context context){
        //Conexao com o banco de dados

        conexao = new ConnectionFactory(context);
        banco = conexao.getWritableDatabase();
    }

    // método inserir
    public long insert(Aluno aluno) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("data_criacao", getCurrentDateTime());

        try {
            return banco.insertOrThrow("aluno", null, values);

        } catch (SQLiteConstraintException e) {
            throw new Exception("Cpf ja cadastrado");
        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar aluno");
        }
    }

    public List<Aluno> obterTodos() {
        List<Aluno> alunos = new ArrayList<>();

        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf",
                        "telefone", "data_criacao"},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome((cursor.getString(1)));
            a.setCpf((cursor.getString(2)));
            a.setTelefone((cursor.getString(3)));
            alunos.add(a);
        }
        cursor.close();
        return alunos;
    }

    public List<Aluno> listarPorNome(){
        List<Aluno> alunos = new ArrayList<>();

        Cursor cursor = banco.rawQuery("SELECT id, nome, cpf, telefone, data_criacao FROM aluno ORDER BY " +
                "CASE " +
                "  WHEN nome COLLATE NOCASE >= 'a' AND nome COLLATE NOCASE <= 'z' THEN 1 " +
                "  ELSE 2 " +
                "END, " +
                "nome COLLATE NOCASE", null);
//                banco.query("aluno", new String[]{"id", "nome", "cpf",
//                        "telefone", "data_criacao"},
//                null, null, null, null, "nome ASC");

        while (cursor.moveToNext()) {
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0));
            a.setNome((cursor.getString(1)));
            a.setCpf((cursor.getString(2)));
            a.setTelefone((cursor.getString(3)));
            alunos.add(a);
        }
        cursor.close();
        return alunos;
    }


    public void update(Aluno aluno) {
        ContentValues values = new ContentValues();
        if (aluno.getNome().length()  >= 1){
            values.put("nome", aluno.getNome());
        }
        else {
            values.put("nome", (read(aluno.getId())).getNome());
        }
        if (aluno.getTelefone().length() >= 1) {
            values.put("telefone", aluno.getTelefone());
        }
        else {
            values.put("telefone", (read(aluno.getId())).getTelefone());
        }
        if(aluno.getCpf().length() >= 1) {
            values.put("cpf", aluno.getCpf());
        }
        else {
            values.put("cpf", (read(aluno.getId())).getCpf());
        }
        String[] args = {String.valueOf(aluno.getId())};
        banco.update("aluno", values,"id=?",args);
    }

    public void delete(Aluno aluno){
        String[] args = {String.valueOf(aluno.getId())};
        banco.delete("aluno","id=?",args);
    }

    public Aluno read(Integer id) {
        String[] args = {String.valueOf(id)};
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
                "id=?", args, null, null, null);
        cursor.moveToFirst();
        Aluno aluno = new Aluno();
        if(cursor.getCount() > 0){
            aluno.setId(cursor.getInt(0));
            aluno.setNome((cursor.getString(1)));
            aluno.setCpf((cursor.getString(2)));
            aluno.setTelefone((cursor.getString(3)));
        }
        cursor.close();
        return aluno;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
