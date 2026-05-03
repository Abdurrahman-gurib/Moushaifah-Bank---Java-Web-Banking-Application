import sqlite3
import os
path = r'C:\Users\noorg\Downloads\Moushaifah_Bank_Java_Servlet_Project\moushaifah-bank-webapp\moushaifah_bank.db'
print('exists', os.path.exists(path))
conn = sqlite3.connect(path)
cur = conn.cursor()
print('tables', cur.execute("select name from sqlite_master where type='table'").fetchall())
print('users', cur.execute("select id,email,passwordHash from users limit 5").fetchall())
conn.close()
