import argparse

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("--name",default=None,type=str,help="input user name")
    print(parser)
    args = parser.parse_args()
    print(args)
    print(args.name)
    print("模型开执行")
